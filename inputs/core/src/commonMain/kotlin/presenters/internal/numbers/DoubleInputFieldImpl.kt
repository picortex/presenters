package presenters.internal.numbers

import formatter.NumberFormatter
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import presenters.Label
import presenters.Formatter
import presenters.internal.utils.DataTransformer

@PublishedApi
internal class DoubleInputFieldImpl(
    override val name: String,
    override val isRequired: Boolean,
    override val label: Label,
    override val hint: String,
    override val isReadonly: Boolean,
    override val max: Double?,
    override val min: Double?,
    override val step: Double?,
    formatter: NumberFormatter?,
    value: Double?,
    validator: ((Double?) -> Unit)?
) : AbstractNumberInputField<Double>(name, isRequired, label, hint, isReadonly, max, min, step, formatter, value, validator) {
    override val serializer: KSerializer<Double> = Double.serializer()
    override val transformer = DataTransformer(
        formatter = Formatter { double ->
            val d = double ?: return@Formatter null
            val fmt = formatter ?: return@Formatter null
            fmt.format(d)
        },
        transformer = { it: String? -> it?.replace(",", "")?.toDoubleOrNull() }
    )

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