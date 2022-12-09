@file:JsExport @file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields.internal

import kotlinx.serialization.KSerializer
import presenters.fields.InputFieldState
import presenters.fields.InputFieldWithValue.Companion.DEFAULT_IS_READONLY
import presenters.fields.InputFieldWithValue.Companion.DEFAULT_IS_REQUIRED
import presenters.fields.TextBasedValuedField
import presenters.fields.ValuedField.Companion.DEFAULT_VALIDATOR
import kotlin.js.JsExport
import kotlin.js.JsName

open class TextBasedValueFieldImpl<T>(
    override val name: String,
    override val label: String = name,
    open val hint: String = label,
    override val transformer: (String?) -> T?,
    override val defaultText: String? = null,
    override val isReadonly: Boolean = DEFAULT_IS_READONLY,
    override val isRequired: Boolean = DEFAULT_IS_REQUIRED,
    open val maxLength: Int? = DEFAULT_MAX_LENGTH,
    open val minLength: Int? = DEFAULT_MIN_LENGTH,
    validator: ((T?) -> Unit)? = DEFAULT_VALIDATOR,
    override val serializer: KSerializer<T>,
) : AbstractValuedField<T>(name, label, transformer(defaultText), isReadonly, isRequired, validator), TextBasedValuedField<T> {
    companion object {
        val DEFAULT_MAX_LENGTH: Int? = null
        val DEFAULT_MIN_LENGTH: Int? = null
    }

    override val defaultValue: T? get() = transformer(defaultText)

    override val valueOrNull: T? get() = this.field.value

    override fun set(text: String) {
        try {
            validate(text = text)
            validate(transformer(text))
            if (feedback.value != InputFieldState.Empty) {
                feedback.value = InputFieldState.Empty
            }
        } catch (err: Throwable) {
            feedback.value = InputFieldState.Warning(err.message ?: "", err)
        }
        field.value = transformer(text)
    }

    @JsName("validateText")
    fun validate(text: String?) {
        if (isRequired && text.isNullOrBlank()) {
            throw IllegalArgumentException("${label.replaceFirstChar { it.uppercase() }} is required")
        }
        val max = maxLength
        if (max != null && text != null && text.length > max) {
            throw IllegalArgumentException("${label.replaceFirstChar { it.uppercase() }} must have less than $max characters")
        }
        val min = minLength
        if (min != null && text != null && text.length < min) {
            throw IllegalArgumentException("${label.replaceFirstChar { it.uppercase() }} must have more than $min characters")
        }
        validator?.invoke(transformer(text))
    }

    override fun validate(value: T?) {
        if (isRequired && value == null) {
            throw IllegalArgumentException("${label.replaceFirstChar { it.uppercase() }} is required")
        }
        if (value is String) validate(text = value)
        validator?.invoke(value)
    }
}