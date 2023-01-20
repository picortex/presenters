package presenters.internal.validators

import live.MutableLive
import presenters.Range
import presenters.fields.InputFieldState
import presenters.internal.utils.FeedbackSetter
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.Validateable2
import presenters.validation.ValidationResult

class RangeValidator2<C : Comparable<C>>(
    override val feedback: MutableLive<InputFieldState>,
    private val isRequired: Boolean,
    private val label: String,
    private val limit: Range<C>?,
) : AbstractValidate1<Range<C>>(feedback), Validateable2<C> {

    override fun validate(value: Range<C>?) = validate(value?.start, value?.end)

    override fun validate(start: C?, end: C?): ValidationResult {
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

    override fun validateSettingInvalidsAsWarnings(start: C?, end: C?) = setFeedbacks(validate(start, end)) {
        InputFieldState.Warning(it.cause.message ?: "", it.cause)
    }

    override fun validateSettingInvalidsAsErrors(start: C?, end: C?) = setFeedbacks(validate(start, end)) {
        InputFieldState.Error(it.cause.message ?: "", it.cause)
    }
}