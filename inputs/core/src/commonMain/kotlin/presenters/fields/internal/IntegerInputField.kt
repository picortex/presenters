package presenters.fields.internal


import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import presenters.fields.InputLabel
import presenters.fields.SingleValuedField

@PublishedApi
internal class IntegerInputField(
    name: String,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    label: InputLabel = InputLabel(name, isRequired),
    hint: String = label.text,
    defaultValue: String? = SingleValuedField.DEFAULT_VALUE,
    formatter: ((Int?) -> String?)? = null,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    override val max: Int? = DEFAULT_MAX,
    override val min: Int? = DEFAULT_MIN,
    override val step: Int = DEFAULT_STEP,
    validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) : AbstractNumberBasedValueField<Int>(name, isRequired, label, hint, defaultValue, formatter, { it?.toIntOrNull() }, isReadonly, max, min, step, validator) {

    override val serializer: KSerializer<Int> = Int.serializer()

    override fun increment(step: Int?) {
        val value = data.value.output ?: 0
        data.value = toInputData(((value) + (step ?: DEFAULT_STEP)).toString())
    }

    override fun decrement(step: Int?) {
        val value = data.value.output ?: 0
        data.value = toInputData(((value) - (step ?: DEFAULT_STEP)).toString())
    }

    companion object {
        val DEFAULT_STEP = 1
    }
}