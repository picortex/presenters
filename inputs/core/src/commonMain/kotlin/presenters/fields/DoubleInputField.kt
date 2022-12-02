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
    override val label: String = name,
    override val hint: String = label,
    override val defaultValue: Double? = ValuedField.DEFAULT_VALUE,
    override val isReadonly: Boolean = InputFieldWithValue.DEFAULT_IS_READONLY,
    override val isRequired: Boolean = InputFieldWithValue.DEFAULT_IS_REQUIRED,
    override val max: Double? = DEFAULT_MAX,
    override val min: Double? = DEFAULT_MIN,
    override val step: Double = DEFAULT_STEP,
    validator: ((Double?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) : NumberBasedValueField<Double>(name, label, hint, defaultValue, isReadonly, isRequired, max, min, step, validator) {
    companion object {
        val DEFAULT_STEP = 1.0
    }

    @JsName("_ignore_fromPropery")
    constructor(
        name: KProperty<*>,
        label: String = name.name,
        hint: String = label,
        defaultValue: Double? = ValuedField.DEFAULT_VALUE,
        isReadonly: Boolean = InputFieldWithValue.DEFAULT_IS_READONLY,
        isRequired: Boolean = InputFieldWithValue.DEFAULT_IS_REQUIRED,
        max: Double? = DEFAULT_MAX,
        min: Double? = DEFAULT_MIN,
        step: Double = DEFAULT_STEP,
        validator: ((Double?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
    ) : this(name.name, label, hint, defaultValue, isReadonly, isRequired, max, min, step, validator)

    override var stringValue: String
        get() = value.toString()
        set(v) = try {
            value = v.toDouble()
        } finally {

        }

    override val serializer: KSerializer<Double> by lazy { Double.serializer() }

    override fun increment(step: Double?) {
        value = (value ?: 0.0) + (step ?: DEFAULT_STEP)
    }

    override fun decrement(step: Double?) {
        value = (value ?: 0.0) - (step ?: DEFAULT_STEP)
    }
}