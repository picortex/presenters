@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields


import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.builtins.serializer
import presenters.fields.internal.NumberBasedValueField
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.reflect.KProperty

class DoubleInputField(
    override val name: String,
    override val isRequired: Boolean = InputFieldWithValue.DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    override val hint: String = label.text,
    override val defaultValue: Double? = ValuedField.DEFAULT_VALUE,
    override val isReadonly: Boolean = InputFieldWithValue.DEFAULT_IS_READONLY,
    override val max: Double? = DEFAULT_MAX,
    override val min: Double? = DEFAULT_MIN,
    override val step: Double = DEFAULT_STEP,
    validator: ((Double?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) : NumberBasedValueField<Double>(name, isRequired, label, hint, defaultValue, isReadonly, max, min, step, validator) {
    companion object {
        val DEFAULT_STEP = 1.0
    }

    override var stringValue: String
        get() = this.field.value.toString()
        set(v) = try {
            this.field.value = v.toDouble()
        } finally {

        }

    override val serializer: KSerializer<Double> by lazy { Double.serializer() }

    override fun increment(step: Double?) {
        field.value = (field.value ?: 0.0) + (step ?: DEFAULT_STEP)
    }

    override fun decrement(step: Double?) {
        field.value = (field.value ?: 0.0) - (step ?: DEFAULT_STEP)
    }
}