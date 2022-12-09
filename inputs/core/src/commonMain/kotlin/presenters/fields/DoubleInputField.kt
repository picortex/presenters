@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields


import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import presenters.fields.internal.NumberBasedValueField
import kotlin.js.JsExport

class DoubleInputField(
    override val name: String,
    override val isRequired: Boolean = InputFieldWithValue.DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    override val hint: String = label.text,
    override val defaultValue: String? = SingleValuedField.DEFAULT_VALUE,
    override val isReadonly: Boolean = InputFieldWithValue.DEFAULT_IS_READONLY,
    override val max: Double? = DEFAULT_MAX,
    override val min: Double? = DEFAULT_MIN,
    override val step: Double = DEFAULT_STEP,
    validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) : NumberBasedValueField<Double>(name, isRequired, label, hint, defaultValue, { it?.toDoubleOrNull() }, isReadonly, max, min, step, validator) {
    companion object {
        val DEFAULT_STEP = 1.0
    }

    override val serializer: KSerializer<Double?> by lazy { Double.serializer().nullable }

    override fun increment(step: Double?) {
        val value = output.value ?: 0.0
        input.value = (value + (step ?: DEFAULT_STEP)).toString()
    }

    override fun decrement(step: Double?) {
        val value = output.value ?: 0.0
        input.value = ((value) - (step ?: DEFAULT_STEP)).toString()
    }
}