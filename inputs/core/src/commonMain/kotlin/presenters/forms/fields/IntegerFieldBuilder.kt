package presenters.forms.fields

import presenters.Label
import presenters.NumberInputField
import presenters.fields.Formatter
import presenters.forms.Fields
import presenters.internal.IntegerInputFieldImpl
import kotlin.reflect.KProperty

fun Fields.integer(
    name: String,
    label: String = name,
    hint: String? = label,
    value: Int? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    formatter: Formatter<Int>? = null,
    max: Int? = null,
    min: Int? = null,
    step: Int = 1,
    validator: ((Int?) -> Unit)? = null
): NumberInputField<Int> = getOrCreate(name) {
    IntegerInputFieldImpl(
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

inline fun Fields.integer(
    property: KProperty<*>,
    label: String = property.name,
    hint: String? = label,
    value: Int? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    formatter: Formatter<Int>? = null,
    max: Int? = null,
    min: Int? = null,
    step: Int = 1,
    noinline validator: ((Int?) -> Unit)? = null
) = integer(property.name, label, hint, value, isReadonly, isRequired, formatter, max, min, step, validator)