package presenters.internal.validators

import live.MutableLive
import presenters.fields.InputFieldState
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

internal class NumberRangeValidator<N : Number>(
    override val feedback: MutableLive<InputFieldState>,
    val label: String,
    val max: N?,
    val min: N?
) : AbstractValidate1<N>(feedback) {
    override fun validate(value: N?): ValidationResult {
        val maxValue = max

        if (maxValue != null && value != null && value.toDouble() > maxValue.toDouble()) {
            return Invalid(IllegalArgumentException("$label should be below than $maxValue"))
        }

        val minValue = min
        if (minValue != null && value != null && value.toDouble() < minValue.toDouble()) {
            return Invalid(IllegalArgumentException("$label should be above than $minValue"))
        }

        return Valid
    }
}