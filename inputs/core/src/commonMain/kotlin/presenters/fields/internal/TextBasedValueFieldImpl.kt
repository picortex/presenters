@file:JsExport @file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields.internal

import kotlinx.serialization.KSerializer
import presenters.fields.InputFieldState
import presenters.fields.InputFieldWithValue.Companion.DEFAULT_IS_READONLY
import presenters.fields.InputFieldWithValue.Companion.DEFAULT_IS_REQUIRED
import presenters.fields.InputLabel
import presenters.fields.Invalid
import presenters.fields.TextBasedValuedField
import presenters.fields.Valid
import presenters.fields.ValidationResult
import presenters.fields.ValuedField.Companion.DEFAULT_VALIDATOR
import kotlin.js.JsExport
import kotlin.js.JsName

open class TextBasedValueFieldImpl<T>(
    override val name: String,
    override val isRequired: Boolean = DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    open val hint: String = label.text,
    override val transformer: (String?) -> T?,
    override val defaultText: String? = null,
    override val isReadonly: Boolean = DEFAULT_IS_READONLY,
    open val maxLength: Int? = DEFAULT_MAX_LENGTH,
    open val minLength: Int? = DEFAULT_MIN_LENGTH,
    validator: ((T?) -> Unit)? = DEFAULT_VALIDATOR,
    override val serializer: KSerializer<T>,
) : AbstractValuedField<T>(name, isRequired, label, transformer(defaultText), isReadonly, validator), TextBasedValuedField<T> {
    companion object {
        val DEFAULT_MAX_LENGTH: Int? = null
        val DEFAULT_MIN_LENGTH: Int? = null
    }

    override val defaultValue: T? get() = transformer(defaultText)

    override val valueOrNull: T? get() = this.field.value

    override fun set(text: String) {
        val res1 = validate(text)
        val v = try {
            transformer(text)
        } catch (err: Throwable) {
            null
        }
        val res2 = validate(v)
        feedback.value = when {
            res1 is Invalid -> InputFieldState.Warning(res1.cause.message ?: "Unknown", res1.cause)
            res2 is Invalid -> InputFieldState.Warning(res2.cause.message ?: "Unknown", res2.cause)
            else -> InputFieldState.Empty
        }
        field.value = v
    }

    @JsName("validateText")
    fun validate(text: String?): ValidationResult {
        if (isRequired && text.isNullOrBlank()) {
            return Invalid(IllegalArgumentException("${label.capitalizedWithoutAstrix()} is required"))
        }
        val max = maxLength
        if (max != null && text != null && text.length > max) {
            return Invalid(IllegalArgumentException("${label.capitalizedWithoutAstrix()} must have less than $max characters"))
        }
        val min = minLength
        if (min != null && text != null && text.length < min) {
            return Invalid(IllegalArgumentException("${label.capitalizedWithoutAstrix()} must have more than $min characters"))
        }
        return try {
            validator?.invoke(transformer(text))
            Valid
        } catch (err: Throwable) {
            Invalid(err)
        }
    }

    override fun validate(value: T?): ValidationResult {
        if (isRequired && value == null) {
            return Invalid(IllegalArgumentException("${label.capitalizedWithoutAstrix()} is required"))
        }
        if (value is String) {
            val res = validate(text = value)
            if (res is Invalid) return res
        }

        return try {
            validator?.invoke(value)
            Valid
        } catch (err: Throwable) {
            Invalid(err)
        }
    }
}