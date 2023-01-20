package presenters.internal.validators

import live.MutableLive
import presenters.fields.InputFieldState
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

class RangeValidator1<C : Comparable<C>>(
    override val feedback: MutableLive<InputFieldState>,
    private val label: String,
    private val max: C?,
    private val min: C?
) : AbstractValidate1<C>(feedback) {
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