@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields.internal

import live.mutableLiveOf
import presenters.fields.InputFieldState
import presenters.fields.InputLabel
import presenters.fields.Invalid
import presenters.fields.Range
import presenters.fields.RangeValuedField
import presenters.fields.SingleValuedField.Companion.DEFAULT_IS_READONLY
import presenters.fields.SingleValuedField.Companion.DEFAULT_IS_REQUIRED
import presenters.fields.SingleValuedField.Companion.DEFAULT_VALIDATOR
import presenters.fields.Valid
import presenters.fields.ValidationResult
import kotlin.js.JsExport

abstract class AbstractRangeField<I, O : Comparable<O>>(
    override val name: String,
    override val isRequired: Boolean = DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    override val transformer: (I?) -> O?,
    override val limit: Range<O>? = null,
    override val isReadonly: Boolean = DEFAULT_IS_READONLY,
    val validator: ((start: I?, end: I?) -> Unit)? = DEFAULT_VALIDATOR
) : RangeValuedField<I, O> {
    override val feedback = mutableLiveOf<InputFieldState>(InputFieldState.Empty)

    override fun clear() {
        start.clear()
        end.clear()
        feedback.value = InputFieldState.Empty
    }

    override fun validate(start: I?, end: I?): ValidationResult {
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

        val b = transformer(start!!)!!
        val max = limit?.end

        if (max != null && b > max) {
            return Invalid(IllegalArgumentException("$tag must be before/less than $max"))
        }

        val e = transformer(end!!)!!
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

    private fun validateSettingFeedback(start: I?, end: I?, body: (res: Invalid) -> InputFieldState): ValidationResult {
        val res = validate(start)
        feedback.value = when (res) {
            is Invalid -> body(res)
            Valid -> InputFieldState.Empty
        }
        return res
    }

    override fun validateSettingInvalidsAsWarnings(start: I?, end: I?) = validateSettingFeedback(start, end) {
        InputFieldState.Warning(it.cause.message ?: "", it.cause)
    }

    override fun validateSettingInvalidsAsErrors(start: I?, end: I?) = validateSettingFeedback(start, end) {
        InputFieldState.Error(it.cause.message ?: "", it.cause)
    }
}