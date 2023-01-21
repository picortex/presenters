package presenters.internal.validators

import live.Live
import live.MutableLive
import presenters.OutputData
import presenters.fields.InputFieldState
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

class TextValidator(
    data: Live<OutputData<String>>,
    feedback: MutableLive<InputFieldState>,
    private val label: String,
    private val isRequired: Boolean,
    private val maxLength: Int?,
    private val minLength: Int?
) : AbstractValidator<String>(data, feedback) {
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