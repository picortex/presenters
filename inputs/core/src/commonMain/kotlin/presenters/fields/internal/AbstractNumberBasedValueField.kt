package presenters.fields.internal

import presenters.fields.InputLabel
import presenters.fields.NumberBasedValuedField
import presenters.fields.SingleValuedField.Companion.DEFAULT_IS_READONLY
import presenters.fields.SingleValuedField.Companion.DEFAULT_IS_REQUIRED
import presenters.fields.SingleValuedField.Companion.DEFAULT_VALIDATOR
import presenters.fields.SingleValuedField.Companion.DEFAULT_VALUE
import presenters.fields.TransformedInput
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

@PublishedApi
internal abstract class AbstractNumberBasedValueField<N : Number>(
    name: String,
    isRequired: Boolean = DEFAULT_IS_REQUIRED,
    label: InputLabel = InputLabel(name, isRequired),
    final override val hint: String = label.text,
    defaultValue: String? = DEFAULT_VALUE,
    formatter: ((N?) -> String?)? = null,
    transformer: (String?) -> N?,
    isReadonly: Boolean = DEFAULT_IS_READONLY,
    open val max: N? = DEFAULT_MAX,
    open val min: N? = DEFAULT_MIN,
    open val step: N? = DEFAULT_STEP,
    validator: ((String?) -> Unit)? = DEFAULT_VALIDATOR
) : TransformedInputValuedField<String, N>(name, isRequired, label, defaultValue, formatter, transformer, isReadonly, validator), NumberBasedValuedField<N>, TransformedInput<String, N> {

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

    override fun type(text: String) {
        val old = data.value.raw ?: ""
        for (i in 0..text.lastIndex) set(old + text.substring(0..i))
    }

    companion object {
        val DEFAULT_STEP: Nothing? = null
        val DEFAULT_MAX: Nothing? = null
        val DEFAULT_MIN: Nothing? = null
    }
}