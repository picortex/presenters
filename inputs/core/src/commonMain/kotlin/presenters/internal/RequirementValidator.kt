package presenters.internal

import live.MutableLive
import live.mutableLiveOf
import presenters.fields.InputFieldState
import presenters.fields.internal.AbstractValidate1
import presenters.fields.properties.Settable
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

internal class RequirementValidator(
    override val feedback: MutableLive<InputFieldState>,
    val label: String,
    val isRequired: Boolean,
) : AbstractValidate1<Any?>() {

    override fun validate(value: Any?): ValidationResult {
        if (isRequired && value == null) {
            return Invalid(IllegalArgumentException("$label is required"))
        }
        return Valid
    }
}