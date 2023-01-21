package presenters.internal.validators

import live.Live
import live.MutableLive
import presenters.OutputData
import presenters.InputFieldState
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

class ClippingValidator<C : Comparable<C>>(
    data: Live<OutputData<C>>,
    feedback: MutableLive<InputFieldState>,
    private val label: String,
    private val max: C?,
    private val min: C?
) : AbstractValidator<C>(data, feedback) {
    override fun validate(value: C?): ValidationResult {
        val tag = label

        val mx = max
        if (mx != null && value != null && value > mx) {
            return Invalid(IllegalArgumentException("$tag must be before $mx"))
        }

        val mn = min
        if (mn != null && value != null && value < mn) {
            return Invalid(IllegalArgumentException("$tag must be after $mn"))
        }

        return Valid
    }
}