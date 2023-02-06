@file:Suppress("NOTHING_TO_INLINE")

package presenters

import formatter.NumberFormatter
import presenters.internal.numbers.DoubleInputFieldImpl
import kotlin.reflect.KProperty

inline fun DoubleInputField(
    name: String,
    label: String = name,
    hint: String? = label,
    value: Double? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    formatter: NumberFormatter? = NumberFormatter(),
    max: Double? = null,
    min: Double? = null,
    step: Double? = 0.1,
    noinline validator: ((Double?) -> Unit)? = null
): NumberInputField<Double> = DoubleInputFieldImpl(
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

inline fun Fields.double(
    name: String,
    label: String = name,
    hint: String? = label,
    value: Double? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    formatter: NumberFormatter? = NumberFormatter(),
    max: Double? = null,
    min: Double? = null,
    step: Double? = 0.1,
    noinline validator: ((Double?) -> Unit)? = null
) = getOrCreate(name) {
    DoubleInputField(name, label, hint, value, isReadonly, isRequired, formatter, max, min, step, validator)
}

inline fun Fields.double(
    name: KProperty<Double?>,
    label: String = name.name,
    hint: String? = label,
    value: Double? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    formatter: NumberFormatter? = NumberFormatter(),
    max: Double? = null,
    min: Double? = null,
    step: Double? = 0.1,
    noinline validator: ((Double?) -> Unit)? = null
) = double(name.name, label, hint, value, isReadonly, isRequired, formatter, max, min, step, validator)