package presenters.fields.internal

import live.mutableLiveOf
import presenters.fields.InputFieldState
import presenters.fields.InputLabel
import presenters.fields.Range
import presenters.fields.RangeValuedField
import presenters.fields.SingleValuedField.Companion.DEFAULT_IS_READONLY
import presenters.fields.SingleValuedField.Companion.DEFAULT_IS_REQUIRED
import presenters.fields.SingleValuedField.Companion.DEFAULT_VALIDATOR
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.Validateable2
import presenters.validation.ValidationResult

abstract class AbstractRangeField<I, O : Comparable<O>>(
    override val name: String,
    override val isRequired: Boolean = DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    override val transformer: (I?) -> O?,
    override val limit: Range<O>? = null,
    override val isReadonly: Boolean = DEFAULT_IS_READONLY,
    val validator: ((start: O?, end: O?) -> Unit)? = DEFAULT_VALIDATOR
) : RangeValuedField<I, O>, Validateable2<O> {
    override val feedback = mutableLiveOf<InputFieldState>(InputFieldState.Empty)

    override fun clear() {
        start.clear()
        end.clear()
        feedback.value = InputFieldState.Empty
    }

    override fun validate(start: O?, end: O?): ValidationResult {
        val tag = label.capitalizedWithoutAstrix()
        if (isRequired && start == null) {
            return Invalid(IllegalArgumentException("$tag start value is required"))
        }

        if (isRequired && end == null) {
            return Invalid(IllegalArgumentException("$tag end value is required"))
        }

        if (start == null && end == null) return Valid

        if (start != null && end == null) {
            return Invalid(IllegalArgumentException("$tag end is required"))
        }

        if (start == null && end != null) {
            return Invalid(IllegalArgumentException("$tag start is required"))
        }

        val b = start!!
        val max = limit?.end

        if (max != null && b > max) {
            return Invalid(IllegalArgumentException("$tag must be before/less than $max"))
        }

        val e = end!!
        val min = limit?.start
        if (min != null && e < min) {
            return Invalid(IllegalArgumentException("$tag must be after/greater than $min"))
        }

        if (b > e) {
            return Invalid(IllegalArgumentException("$tag can't range from $start to $end"))
        }

        return try {
            validator?.invoke(start, end)
            Valid
        } catch (err: Throwable) {
            Invalid(err)
        }
    }

    override fun validate() = data.value.output.let { validate(it?.start, it?.end) }

    private fun validateSettingFeedback(start: O?, end: O?, body: (res: Invalid) -> InputFieldState): ValidationResult {
        val res = validate(start, end)
        feedback.value = when (res) {
            is Invalid -> body(res)
            Valid -> InputFieldState.Empty
        }
        return res
    }

    override fun validateSettingInvalidsAsWarnings(start: O?, end: O?) = validateSettingFeedback(start, end) {
        InputFieldState.Warning(it.cause.message ?: "", it.cause)
    }

    override fun validateSettingInvalidsAsWarnings() = data.value.output.let { validateSettingInvalidsAsErrors(it?.start, it?.end) }

    override fun validateSettingInvalidsAsErrors(start: O?, end: O?) = validateSettingFeedback(start, end) {
        InputFieldState.Error(it.cause.message ?: "", it.cause)
    }

    override fun validateSettingInvalidsAsErrors() = data.value.output.let { validateSettingInvalidsAsErrors(it?.start, it?.end) }
}