@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields.internal

import presenters.fields.InputLabel
import presenters.fields.Invalid
import presenters.fields.SingleValuedField.Companion.DEFAULT_IS_READONLY
import presenters.fields.SingleValuedField.Companion.DEFAULT_IS_REQUIRED
import presenters.fields.SingleValuedField.Companion.DEFAULT_VALIDATOR
import presenters.fields.SingleValuedField.Companion.DEFAULT_VALUE
import presenters.fields.Valid
import presenters.fields.ValidationResult
import kotlin.js.JsExport

abstract class NumberBasedValueField<N : Number>(
    override val name: String,
    override val isRequired: Boolean = DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    open val hint: String = label.text,
    defaultValue: String? = DEFAULT_VALUE,
    override val transformer: (String?) -> N?,
    override val isReadonly: Boolean = DEFAULT_IS_READONLY,
    open val max: N? = DEFAULT_MAX,
    open val min: N? = DEFAULT_MIN,
    open val step: N? = DEFAULT_STEP,
    validator: ((String?) -> Unit)? = DEFAULT_VALIDATOR
) : AbstractValuedField<String, N>(name, isRequired, label, defaultValue, transformer, isReadonly, validator) {
    companion object {
        val DEFAULT_STEP: Nothing? = null
        val DEFAULT_MAX: Nothing? = null
        val DEFAULT_MIN: Nothing? = null
    }

    abstract fun increment(step: N? = this.step)

    abstract fun decrement(step: N? = this.step)

    fun set(value: N?) = set(value.toString())

    override fun validate(value: String?): ValidationResult {
        if (isRequired && value == null) {
            return Invalid(IllegalArgumentException("${label.capitalizedWithoutAstrix()} is required"))
        }
        val maxValue = max
        if (maxValue != null && value != null && value.toDouble() > maxValue.toDouble()) {
            return Invalid(IllegalArgumentException("${label.capitalizedWithoutAstrix()}} should be below than $maxValue"))
        }
        val minValue = min
        if (minValue != null && value != null && value.toDouble() < minValue.toDouble()) {
            return Invalid(IllegalArgumentException("${label.capitalizedWithoutAstrix()}} should be above than $minValue"))
        }

        return try {
            validator?.invoke(value)
            Valid
        } catch (err: Throwable) {
            Invalid(err)
        }
    }
}