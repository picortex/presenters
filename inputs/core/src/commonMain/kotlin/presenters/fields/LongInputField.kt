@file:JsExport @file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields


import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.builtins.serializer
import presenters.fields.internal.NumberBasedValueField
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.reflect.KProperty

class LongInputField(
    override val name: String,
    override val isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    override val hint: String = label.text,
    override val defaultValue: Long? = ValuedField.DEFAULT_VALUE,
    override val isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    override val max: Long? = DEFAULT_MAX,
    override val min: Long? = DEFAULT_MIN,
    override val step: Long = DEFAULT_STEP,
    validator: ((Long?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) : NumberBasedValueField<Long>(name, isRequired, label, hint, defaultValue, isReadonly, max, min, step, validator) {
    companion object {
        val DEFAULT_STEP = 1L
    }

    override val serializer: KSerializer<Long> by lazy { Long.serializer() }
    override var stringValue: String
        get() = this.field.value.toString()
        set(v) = try {
            this.field.value = v.toLong()
        } finally {

        }

    val valueAsDouble get() = field.value?.toDouble()
    val maxAsDouble get() = this.max?.toDouble()
    val minAsDouble get() = this.min?.toDouble()
    val stepAsDouble get() = this.step.toDouble()

    override fun increment(step: Long?) {
        field.value = (field.value ?: 0) + (step ?: DEFAULT_STEP)
    }

    override fun decrement(step: Long?) {
        field.value = (field.value ?: 0) - (step ?: DEFAULT_STEP)
    }
}