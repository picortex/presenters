package presenters.forms.fields

import presenters.Label
import presenters.NumberInputField
import presenters.fields.Formatter
import presenters.forms.Fields
import presenters.internal.LongInputFieldImpl
import kotlin.reflect.KProperty

fun Fields.long(
    name: String,
    label: String = name,
    hint: String? = label,
    value: Long? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    formatter: Formatter<Long>? = null,
    max: Long? = null,
    min: Long? = null,
    step: Long = 1,
    validator: ((Long?) -> Unit)? = null
): NumberInputField<Long> = getOrCreate(name) {
    LongInputFieldImpl(
        name = name,
        label = Label(label, isReadonly),
        hint = hint ?: name,
        value = value,
        formatter = formatter,
        isReadonly = isReadonly,
        isRequired = isRequired,
        max = max,
        min = min,
        step = step,
        validator = validator,
    )
}

inline fun Fields.long(
    property: KProperty<*>,
    label: String = property.name,
    hint: String? = label,
    value: Long? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    formatter: Formatter<Long>? = null,
    max: Long? = null,
    min: Long? = null,
    step: Long = 1,
    noinline validator: ((Long?) -> Unit)? = null
) = long(property.name, label, hint, value, isReadonly, isRequired, formatter, max, min, step, validator)