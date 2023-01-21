package presenters.internal.numbers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import presenters.Formatter
import presenters.Label
import presenters.internal.utils.DataTransformer

@PublishedApi
internal class LongInputFieldImpl(
    name: String,
    isRequired: Boolean = false,
    label: Label = Label(name, isRequired),
    hint: String = label.text,
    isReadonly: Boolean = false,
    max: Long? = null,
    min: Long? = null,
    step: Long? = null,
    formatter: Formatter<Long>? = null,
    value: Long? = null,
    validator: ((Long?) -> Unit)? = null
) : AbstractNumberInputField<Long>(name, isRequired, label, hint, isReadonly, max, min, step, formatter, value, validator) {
    override val serializer: KSerializer<Long> = Long.serializer()
    override val transformer = DataTransformer(formatter) { it: String? -> it?.replace(",", "")?.toLongOrNull() }

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