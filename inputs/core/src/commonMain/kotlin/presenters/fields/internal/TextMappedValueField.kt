@file:JsExport @file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields.internal

import kotlinx.serialization.KSerializer
import presenters.fields.InputFieldState
import presenters.fields.InputFieldWithValue.Companion.DEFAULT_IS_READONLY
import presenters.fields.InputFieldWithValue.Companion.DEFAULT_IS_REQUIRED
import presenters.fields.ValuedField.Companion.DEFAULT_VALIDATOR
import kotlin.js.JsExport
import kotlin.js.JsName

open class TextMappedValueField<T : Any>(
    override val name: String,
    override val label: String = name,
    open val hint: String = label,
    val transformer: (String?) -> T,
    defaultText: String? = null,
    override val isReadonly: Boolean = DEFAULT_IS_READONLY,
    override val isRequired: Boolean = DEFAULT_IS_REQUIRED,
    open val maxLength: Int? = DEFAULT_MAX_LENGTH,
    open val minLength: Int? = DEFAULT_MIN_LENGTH,
    validator: ((T?) -> Unit)? = DEFAULT_VALIDATOR,
    override val serializer: KSerializer<T>,
) : AbstractValuedField<T>(name, label, transformer(defaultText), isReadonly, isRequired, validator) {
    companion object {
        val DEFAULT_MAX_LENGTH: Int? = null
        val DEFAULT_MIN_LENGTH: Int? = null
    }

    override val defaultValue: T? = transformer(defaultText)

    open var text: String? = defaultText
        set(v) {
            update(v)
            value = try {
                transformer(v)
            } catch (err: Throwable) {
                null
            }
            field = v
        }

    private fun update(value: String?) {
        try {
            validate(value)
            if (feedback.value != InputFieldState.Empty) {
                feedback.value = InputFieldState.Empty
            }
        } catch (err: Throwable) {
            feedback.value = InputFieldState.Warning(err.message ?: "", err)
        }
    }

    @JsName("validateText")
    fun validate(value: String?) {
        if (isRequired && value.isNullOrBlank()) {
            throw IllegalArgumentException("${label.replaceFirstChar { it.uppercase() }} is required")
        }
        val max = maxLength
        if (max != null && value != null && value.length > max) {
            throw IllegalArgumentException("${label.replaceFirstChar { it.uppercase() }} must have less than $max characters")
        }
        val min = minLength
        if (min != null && value != null && value.length < min) {
            throw IllegalArgumentException("${label.replaceFirstChar { it.uppercase() }} must have more than $min characters")
        }
        validator?.invoke(transformer(value))
    }

    override fun validate(value: T?) {
        validator?.invoke(value)
    }
}