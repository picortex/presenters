package presenters.internal.validators

import live.Live
import live.MutableLive
import presenters.OutputData
import presenters.Range
import presenters.fields.InputFieldState
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

class RangeValidator<C : Comparable<C>>(
    data: Live<OutputData<Range<C>>>,
    feedback: MutableLive<InputFieldState>,
    private val isRequired: Boolean,
    private val label: String,
    private val limit: Range<C>?,
) : AbstractValidator<Range<C>>(data, feedback) {

    override fun validate(value: Range<C>?) = validate(value?.start, value?.end)

    private fun validate(start: C?, end: C?): ValidationResult {
        if (isRequired && start == null) {
            return Invalid(IllegalArgumentException("$label start value is required"))
        }

        if (isRequired && end == null) {
            return Invalid(IllegalArgumentException("$label end value is required"))
        }

        if (start == null && end == null) return Valid

        if (start != null && end == null) {
            return Invalid(IllegalArgumentException("$label end is required"))
        }

        if (start == null && end != null) {
            return Invalid(IllegalArgumentException("$label start is required"))
        }

        val b = start!!
        val max = limit?.end

        if (max != null && b > max) {
            return Invalid(IllegalArgumentException("$label must be before/less than $max"))
        }

        val e = end!!
        val min = limit?.start
        if (min != null && e < min) {
            return Invalid(IllegalArgumentException("$label must be after/greater than $min"))
        }

        if (b > e) {
            return Invalid(IllegalArgumentException("$label can't range from $start to $end"))
        }

        return Valid
    }
}