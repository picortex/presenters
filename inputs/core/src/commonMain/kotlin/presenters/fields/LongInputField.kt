@file:JsExport @file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields


import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import presenters.fields.internal.NumberBasedValueField
import kotlin.js.JsExport

class LongInputField(
    override val name: String,
    override val isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    override val hint: String = label.text,
    override val defaultValue: String? = SingleValuedField.DEFAULT_VALUE,
    override val isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    override val max: Long? = DEFAULT_MAX,
    override val min: Long? = DEFAULT_MIN,
    override val step: Long = DEFAULT_STEP,
    validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) : NumberBasedValueField<Long>(name, isRequired, label, hint, defaultValue, { it?.toLongOrNull() }, isReadonly, max, min, step, validator) {
    companion object {
        val DEFAULT_STEP = 1L
    }

    override val serializer: KSerializer<Long?> by lazy { Long.serializer().nullable }

    val valueAsDouble get() = output.value?.toDouble()
    val maxAsDouble get() = this.max?.toDouble()
    val minAsDouble get() = this.min?.toDouble()
    val stepAsDouble get() = this.step.toDouble()

    override fun increment(step: Long?) {
        val value = output.value ?: 0
        input.value = ((value) + (step ?: DEFAULT_STEP)).toString()
    }

    override fun decrement(step: Long?) {
        val value = output.value ?: 0
        input.value = ((value) - (step ?: DEFAULT_STEP)).toString()
    }
}