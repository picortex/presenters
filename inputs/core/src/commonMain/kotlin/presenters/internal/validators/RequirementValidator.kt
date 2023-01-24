package presenters.internal.validators

import live.Live
import live.MutableLive
import presenters.Data
import presenters.InputFieldState
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

class RequirementValidator<T>(
    override val data: Live<Data<T>>,
    override val feedback: MutableLive<InputFieldState>,
    private val label: String,
    private val isRequired: Boolean,
) : AbstractValidator<T>(feedback) {

    override fun validate(value: T?): ValidationResult {
        val message = IllegalArgumentException("$label is required")
        if (isRequired && value == null) {
            return Invalid(message)
        }

        if (isRequired && value is Collection<Any?> && value.isEmpty()) {
            return Invalid(message)
        }
        return Valid
    }
}