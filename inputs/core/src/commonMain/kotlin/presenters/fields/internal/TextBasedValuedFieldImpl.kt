package presenters.fields.internal

import kotlinx.serialization.KSerializer
import presenters.fields.InputFieldWithValue.Companion.DEFAULT_IS_READONLY
import presenters.fields.InputFieldWithValue.Companion.DEFAULT_IS_REQUIRED
import presenters.fields.InputLabel
import presenters.fields.Invalid
import presenters.fields.TextBasedValuedField
import presenters.fields.Valid
import presenters.fields.ValidationResult
import presenters.fields.SingleValuedField.Companion.DEFAULT_VALIDATOR

class TextBasedValuedFieldImpl<O : Any>(
    override val name: String,
    override val isRequired: Boolean = DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    open val hint: String = label.capitalizedWithAstrix(),
    override val transformer: (String?) -> O?,
    override val defaultValue: String? = null,
    override val isReadonly: Boolean = DEFAULT_IS_READONLY,
    open val maxLength: Int? = DEFAULT_MAX_LENGTH,
    open val minLength: Int? = DEFAULT_MIN_LENGTH,
    validator: ((String?) -> Unit)? = DEFAULT_VALIDATOR,
    override val serializer: KSerializer<O>,
) : AbstractValuedField<String, O>(name, isRequired, label, defaultValue, transformer, isReadonly, validator), TextBasedValuedField<O> {
    companion object {
        val DEFAULT_MAX_LENGTH: Int? = null
        val DEFAULT_MIN_LENGTH: Int? = null
    }

    override fun validate(value: String?): ValidationResult {
        if (isRequired && value.isNullOrBlank()) {
            return Invalid(IllegalArgumentException("${label.capitalizedWithoutAstrix()} is required"))
        }
        val max = maxLength
        if (max != null && value != null && value.length > max) {
            return Invalid(IllegalArgumentException("${label.capitalizedWithoutAstrix()} must have less than $max characters"))
        }
        val min = minLength
        if (min != null && value != null && value.length < min) {
            return Invalid(IllegalArgumentException("${label.capitalizedWithoutAstrix()} must have more than $min characters"))
        }

        return try {
            validator?.invoke(value)
            Valid
        } catch (err: Throwable) {
            Invalid(err)
        }
    }
}