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

class IntegerInputField(
    override val name: String,
    override val label: String = name,
    override val hint: String = label,
    override val defaultValue: Int? = ValuedField.DEFAULT_VALUE,
    override val isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    override val isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    override val max: Int? = DEFAULT_MAX,
    override val min: Int? = DEFAULT_MIN,
    override val step: Int = DEFAULT_STEP,
    validator: ((Int?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) : NumberBasedValueField<Int>(name, label, hint, defaultValue, isReadonly, isRequired, max, min, step, validator) {
    companion object {
        val DEFAULT_STEP = 1
    }

    @JsName("_ignore_fromPropery")
    constructor(
        name: KProperty<*>,
        label: String = name.name,
        hint: String = label,
        defaultValue: Int? = ValuedField.DEFAULT_VALUE,
        isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
        isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
        max: Int? = DEFAULT_MAX,
        min: Int? = DEFAULT_MIN,
        step: Int = DEFAULT_STEP,
        validator: ((Int?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
    ) : this(name.name, label, hint, defaultValue, isReadonly, isRequired, max, min, step, validator)


    override val serializer: KSerializer<Int> by lazy { Int.serializer() }
    override var stringValue: String
        get() = this.field.value.toString()
        set(v) = try {
            this.field.value = v.toInt()
        } finally {

        }

    override fun increment(step: Int?) {
        field.value = (field.value ?: 0) + (step ?: DEFAULT_STEP)
    }

    override fun decrement(step: Int?) {
        field.value = (field.value ?: 0) - (step ?: DEFAULT_STEP)
    }
}