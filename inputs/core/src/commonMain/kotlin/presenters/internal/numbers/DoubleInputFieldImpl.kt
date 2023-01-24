package presenters.internal.numbers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import presenters.Label
import presenters.Formatter
import presenters.internal.utils.DataTransformer

@PublishedApi
internal class DoubleInputFieldImpl(
    override val name: String,
    override val isRequired: Boolean = false,
    override val label: Label = Label(name, isRequired),
    override val hint: String = label.text,
    override val isReadonly: Boolean = false,
    override val max: Double? = null,
    override val min: Double? = null,
    override val step: Double? = null,
    formatter: Formatter<Double>? = null,
    value: Double? = null,
    validator: ((Double?) -> Unit)? = null
) : AbstractNumberInputField<Double>(name, isRequired, label, hint, isReadonly, max, min, step, formatter, value, validator) {
    override val serializer: KSerializer<Double> = Double.serializer()
    override val transformer = DataTransformer(formatter) { it: String? -> it?.replace(",","")?.toDoubleOrNull() }

    override fun increment(step: Double?) {
        val value = data.value.output ?: DEFAULT_NUMBER
        setter.set((value + (step ?: DEFAULT_STEP)).toString())
    }

    override fun decrement(step: Double?) {
        val value = data.value.output ?: DEFAULT_NUMBER
        setter.set((value - (step ?: DEFAULT_STEP)).toString())
    }

    companion object {
        val DEFAULT_STEP = 1.0
        val DEFAULT_NUMBER = 0.0
    }
}