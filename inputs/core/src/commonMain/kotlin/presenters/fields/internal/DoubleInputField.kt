package presenters.fields.internal

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import presenters.fields.InputLabel
import presenters.fields.SingleValuedField

@PublishedApi
internal class DoubleInputField(
    name: String,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    label: InputLabel = InputLabel(name, isRequired),
    hint: String = label.text,
    defaultValue: String? = SingleValuedField.DEFAULT_VALUE,
    formatter: ((Double?) -> String?)? = null,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    override val max: Double? = DEFAULT_MAX,
    override val min: Double? = DEFAULT_MIN,
    override val step: Double? = DEFAULT_STEP,
    validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) : AbstractNumberBasedValueField<Double>(name, isRequired, label, hint, defaultValue, formatter, { it?.toDoubleOrNull() }, isReadonly, max, min, step, validator) {

    override val serializer: KSerializer<Double> = Double.serializer()

    override fun increment(step: Double?) {
        val value = data.value.output ?: 0.0
        data.value = toInputData((value + (step ?: DEFAULT_STEP)).toString())
    }

    override fun decrement(step: Double?) {
        val value = data.value.output ?: 0.0
        data.value = toInputData((value - (step ?: DEFAULT_STEP)).toString())
    }

    companion object {
        val DEFAULT_STEP = 1.0
    }
}