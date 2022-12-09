@file:JsExport @file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields.internal

import live.mutableLiveOf
import presenters.fields.InputFieldState
import presenters.fields.InputLabel
import presenters.fields.Invalid
import presenters.fields.Range
import presenters.fields.RangeField
import presenters.fields.Valid
import presenters.fields.ValidationResult
import presenters.fields.ValuedField
import presenters.fields.ValuedField.Companion.DEFAULT_IS_READONLY
import presenters.fields.ValuedField.Companion.DEFAULT_IS_REQUIRED
import presenters.fields.ValuedField.Companion.DEFAULT_VALIDATOR
import kotlin.js.JsExport

abstract class AbstractRangeField<T : Comparable<T>>(
    override val name: String,
    override val isRequired: Boolean = DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    defaultValue: Range<T>? = ValuedField.DEFAULT_VALUE,
    override val limit: Range<T>? = RangeField.DEFAULT_LIMIT,
    override val isReadonly: Boolean = DEFAULT_IS_READONLY,
    validator: ((Range<T>?) -> Unit)? = DEFAULT_VALIDATOR
) : AbstractValuedField<Range<T>>(name, isRequired, label, defaultValue, isReadonly, validator), RangeField<T> {
    override val feedback = mutableLiveOf<InputFieldState>(InputFieldState.Empty)

    override fun set(value: Range<T>?) {
        val s = value?.start
        val e = value?.end
        field.value = if (s != null && e != null && s <= e) Range(s, e) else defaultValue
    }

    override var start: T? = null
        get() = field ?: defaultValue?.start
        set(value) {
            update(value, end)
            field = value
        }

    override var end: T? = null
        get() = field ?: defaultValue?.end
        set(value) {
            update(start, value)
            field = value
        }

    protected fun update(start: T?, end: T?) {
        try {
            validate(start, end)
            if (start != null && end != null) {
                field.value = Range(start, end)
            }
            if (feedback.value != InputFieldState.Empty) {
                feedback.value = InputFieldState.Empty
            }
        } catch (err: Throwable) {
            feedback.value = InputFieldState.Warning(err.message ?: "", err)
        }
    }

    override fun clear() {
        start = null
        end = null
        feedback.value = InputFieldState.Empty
    }

    override fun validate(value: Range<T>?) = validate(value?.start, value?.end)

    protected fun validate(start: T?, end: T?): ValidationResult {
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

        val s = start!!
        val max = limit?.end

        if (max != null && s > max) {
            return Invalid(IllegalArgumentException("$tag must be before/less than $max"))
        }

        val e = end!!
        val min = limit?.start
        if (min != null && e < min) {
            return Invalid(IllegalArgumentException("$tag must be after/greater than $min"))
        }

        if (s > e) {
            return Invalid(IllegalArgumentException("$tag can't range from $start to $end"))
        }

        return try {
            validator?.invoke(field.value)
            Valid
        } catch (err: Throwable) {
            Invalid(err)
        }
    }

    override fun validateSettingInvalidsAsWarnings(value: Range<T>?): ValidationResult {
        val res = validate(value?.start, value?.end)
        feedback.value = when (res) {
            is Invalid -> InputFieldState.Warning(res.cause.message ?: "", res.cause)
            Valid -> InputFieldState.Empty
        }
        return res
    }

    override fun validateSettingInvalidsAsErrors(value: Range<T>?): ValidationResult {
        val res = validate(value?.start, value?.end)
        feedback.value = when (res) {
            is Invalid -> InputFieldState.Error(res.cause.message ?: "", res.cause)
            Valid -> InputFieldState.Empty
        }
        return res
    }
}