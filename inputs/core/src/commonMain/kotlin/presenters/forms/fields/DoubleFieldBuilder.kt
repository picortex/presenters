package presenters.forms.fields

import presenters.Label
import presenters.NumberInputField
import presenters.fields.Formatter
import presenters.forms.Fields
import presenters.internal.DoubleInputFieldImpl
import kotlin.reflect.KProperty

fun Fields.double(
    name: String,
    label: String = name,
    hint: String? = label,
    value: Double? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    formatter: Formatter<Double>? = null,
    max: Double? = null,
    min: Double? = null,
    step: Double = 0.1,
    validator: ((Double?) -> Unit)? = null
): NumberInputField<Double> = getOrCreate(name) {
    DoubleInputFieldImpl(
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

inline fun Fields.double(
    property: KProperty<*>,
    label: String = property.name,
    hint: String? = label,
    value: Double? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    formatter: Formatter<Double>? = null,
    max: Double? = null,
    min: Double? = null,
    step: Double = 0.1,
    noinline validator: ((Double?) -> Unit)? = null
) = double(property.name, label, hint, value, isReadonly, isRequired, formatter, max, min, step, validator)