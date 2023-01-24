package presenters.internal.numbers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import presenters.Formatter
import presenters.Label
import presenters.internal.utils.DataTransformer

@PublishedApi
internal class IntegerInputFieldImpl(
    override val name: String,
    override val isRequired: Boolean = false,
    override val label: Label = Label(name, isRequired),
    override val hint: String = label.text,
    override val isReadonly: Boolean = false,
    override val max: Int? = null,
    override val min: Int? = null,
    override val step: Int? = null,
    formatter: Formatter<Int>? = null,
    value: Int? = null,
    validator: ((Int?) -> Unit)? = null
) : AbstractNumberInputField<Int>(name, isRequired, label, hint, isReadonly, max, min, step, formatter, value, validator) {
    override val serializer: KSerializer<Int> = Int.serializer()
    override val transformer = DataTransformer(formatter) { it: String? -> it?.replace(",","")?.toIntOrNull() }

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