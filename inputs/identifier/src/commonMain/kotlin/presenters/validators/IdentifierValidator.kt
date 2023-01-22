package presenters.validators

import identifier.Phone
import live.MutableLive
import presenters.InputFieldState
import presenters.OutputData
import presenters.internal.validators.AbstractValidator
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

abstract class IdentifierValidator(
    data: MutableLive<OutputData<String>>,
    feedback: MutableLive<InputFieldState>,
    private val label: String,
    private val isRequired: Boolean,
) : AbstractValidator<String>(data, feedback) {

    fun purifyThen(value: String?, block: (String) -> Unit): ValidationResult {
        if (isRequired && value == null) return Invalid(IllegalArgumentException("$label is Required"))
        if (!isRequired && value == null) return Valid
        val v: String = value ?: return Invalid(IllegalStateException("$label is Required"))
        return try {
            block(v)
            Valid
        } catch (err: Throwable) {
            Invalid(err)
        }
    }
}