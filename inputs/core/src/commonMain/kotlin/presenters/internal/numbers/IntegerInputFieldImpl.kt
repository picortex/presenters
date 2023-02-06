package presenters.internal.numbers

import formatter.NumberFormatter
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import presenters.Formatter
import presenters.Label
import presenters.internal.utils.DataTransformer

@PublishedApi
internal class IntegerInputFieldImpl(
    override val name: String,
    override val isRequired: Boolean,
    override val label: Label,
    override val hint: String,
    override val isReadonly: Boolean,
    override val max: Int?,
    override val min: Int?,
    override val step: Int?,
    formatter: NumberFormatter?,
    value: Int?,
    validator: ((Int?) -> Unit)?
) : AbstractNumberInputField<Int>(name, isRequired, label, hint, isReadonly, max, min, step, formatter, value, validator) {
    override val serializer: KSerializer<Int> = Int.serializer()
    override val transformer = DataTransformer(
        formatter = Formatter { int ->
            val i = int ?: return@Formatter null
            val fmt = formatter ?: return@Formatter null
            fmt.format(i)
        },
        transformer = { it: String? -> it?.replace(",", "")?.toIntOrNull() }
    )

    override fun increment(step: Int?) {
        val value = data.value.output ?: DEFAULT_NUMBER
        setter.set((value + (step ?: DEFAULT_STEP)).toString())
    }

    override fun decrement(step: Int?) {
        val value = data.value.output ?: DEFAULT_NUMBER
        setter.set((value - (step ?: DEFAULT_STEP)).toString())
    }

    companion object {
        val DEFAULT_STEP = 1
        val DEFAULT_NUMBER = 0
    }
}