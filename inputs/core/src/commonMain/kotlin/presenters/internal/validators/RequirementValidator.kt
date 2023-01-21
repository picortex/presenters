package presenters.internal.validators

import live.Live
import live.MutableLive
import presenters.OutputData
import presenters.fields.InputFieldState
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

class RequirementValidator(
    data: Live<OutputData<Any?>>,
    feedback: MutableLive<InputFieldState>,
    private val label: String,
    private val isRequired: Boolean,
) : AbstractValidator<Any?>(data, feedback) {

    override fun validate(value: Any?): ValidationResult {
        val message = IllegalArgumentException("$label is required")
        if (isRequired && value == null) {
            return Invalid(message)
        }

        if (isRequired && value is Collection<*> && value.isEmpty()) {
            return Invalid(message)
        }
        return Valid
    }
}