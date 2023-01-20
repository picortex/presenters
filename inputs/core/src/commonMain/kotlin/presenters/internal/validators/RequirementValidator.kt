package presenters.internal.validators

import live.MutableLive
import presenters.fields.InputFieldState
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

class RequirementValidator(
    override val feedback: MutableLive<InputFieldState>,
    val label: String,
    val isRequired: Boolean,
) : AbstractValidate1<Any?>(feedback) {

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