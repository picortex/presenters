package presenters.internal.validators

import live.MutableLive
import presenters.fields.InputFieldState
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

class TextValidator(
    override val feedback: MutableLive<InputFieldState>,
    val label: String,
    val isRequired: Boolean,
    val maxLength: Int?,
    val minLength: Int?
) : AbstractValidate1<String>(feedback) {
    override fun validate(value: String?): ValidationResult {
        if (isRequired && value.isNullOrBlank()) {
            return Invalid(IllegalArgumentException("$label is required"))
        }
        val max = maxLength
        if (max != null && value != null && value.length > max) {
            return Invalid(IllegalArgumentException("$label must have less than $max characters"))
        }
        val min = minLength
        if (min != null && value != null && value.length < min) {
            return Invalid(IllegalArgumentException("$label must have more than $min characters"))
        }
        return Valid
    }
}