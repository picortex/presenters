@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields


import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import presenters.fields.internal.NumberBasedValueField
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.reflect.KProperty

class IntegerInputField(
    override val name: String,
    override val isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    override val hint: String = label.text,
    override val defaultValue: String? = SingleValuedField.DEFAULT_VALUE,
    override val isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    override val max: Int? = DEFAULT_MAX,
    override val min: Int? = DEFAULT_MIN,
    override val step: Int = DEFAULT_STEP,
    validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) : NumberBasedValueField<Int>(name, isRequired, label, hint, defaultValue, { it?.toIntOrNull() }, isReadonly, max, min, step, validator) {
    companion object {
        val DEFAULT_STEP = 1
    }

    override val serializer: KSerializer<Int?> by lazy { Int.serializer().nullable }

    override fun increment(step: Int?) {
        val value = output.value ?: 0
        input.value = ((value) + (step ?: DEFAULT_STEP)).toString()
    }

    override fun decrement(step: Int?) {
        val value = output.value ?: 0
        input.value = ((value) - (step ?: DEFAULT_STEP)).toString()
    }
}