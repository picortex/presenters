package presenters.fields.internal

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import presenters.fields.Formatter
import presenters.Label
import presenters.fields.SingleValuedField

@PublishedApi
internal class LongInputField(
    name: String,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    label: Label = Label(name, isRequired),
    hint: String = label.text,
    defaultValue: String? = SingleValuedField.DEFAULT_VALUE,
    formatter: Formatter<Long>? = null,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    override val max: Long? = DEFAULT_MAX,
    override val min: Long? = DEFAULT_MIN,
    override val step: Long = DEFAULT_STEP,
    validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) : AbstractNumberBasedValueField<Long>(name, isRequired, label, hint, defaultValue, formatter, { it?.toLongOrNull() }, isReadonly, max, min, step, validator) {

    val serializer: KSerializer<Long> = Long.serializer()

    val valueAsDouble get() = data.value.output?.toDouble()
    val maxAsDouble = this.max?.toDouble()
    val minAsDouble = this.min?.toDouble()
    val stepAsDouble = this.step.toDouble()

    override fun increment(step: Long?) {
        val value = data.value.output ?: 0
        data.value = toFormattedData(((value) + (step ?: DEFAULT_STEP)).toString())
    }

    override fun decrement(step: Long?) {
        val value = data.value.output ?: 0
        data.value = toFormattedData(((value) - (step ?: DEFAULT_STEP)).toString())
    }

    companion object {
        val DEFAULT_STEP = 1L
    }
}