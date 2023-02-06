package presenters.internal.numbers

import formatter.NumberFormatter
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import presenters.Formatter
import presenters.Label
import presenters.internal.utils.DataTransformer

@PublishedApi
internal class LongInputFieldImpl(
    override val name: String,
    override val isRequired: Boolean,
    override val label: Label,
    override val hint: String,
    override val isReadonly: Boolean,
    override val max: Long?,
    override val min: Long?,
    override val step: Long?,
    formatter: NumberFormatter?,
    value: Long?,
    validator: ((Long?) -> Unit)?
) : AbstractNumberInputField<Long>(name, isRequired, label, hint, isReadonly, max, min, step, formatter, value, validator) {
    override val serializer: KSerializer<Long> = Long.serializer()
    override val transformer = DataTransformer(
        formatter = Formatter { long ->
            val l = long ?: return@Formatter null
            val fmt = formatter ?: return@Formatter null
            fmt.format(l)
        },
        transformer = { it: String? -> it?.replace(",", "")?.toLongOrNull() }
    )

    override fun increment(step: Long?) {
        val value = data.value.output ?: DEFAULT_NUMBER
        setter.set((value + (step ?: DEFAULT_STEP)).toString())
    }

    override fun decrement(step: Long?) {
        val value = data.value.output ?: DEFAULT_NUMBER
        setter.set((value - (step ?: DEFAULT_STEP)).toString())
    }

    companion object {
        val DEFAULT_STEP = 1L
        val DEFAULT_NUMBER = 0L
    }
}