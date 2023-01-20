package presenters.forms.fields

import presenters.fields.Formatter
import presenters.forms.Fields
import kotlin.reflect.KProperty

fun Fields.number(
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
) = double(name, label, hint, value, isReadonly, isRequired, formatter, max, min, step, validator)

inline fun Fields.number(
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