@file:Suppress("NOTHING_TO_INLINE")

package presenters

import formatter.NumberFormatter
import presenters.internal.numbers.IntegerInputFieldImpl
import kotlin.reflect.KProperty

inline fun IntegerInputField(
    name: String,
    label: String = name,
    hint: String? = label,
    value: Int? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    formatter: NumberFormatter? = NumberFormatter(),
    max: Int? = null,
    min: Int? = null,
    step: Int = 1,
    noinline validator: ((Int?) -> Unit)? = null
): NumberInputField<Int> = IntegerInputFieldImpl(
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

inline fun Fields.integer(
    name: String,
    label: String = name,
    hint: String? = label,
    value: Int? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    formatter: NumberFormatter? = NumberFormatter(),
    max: Int? = null,
    min: Int? = null,
    step: Int = 1,
    noinline validator: ((Int?) -> Unit)? = null
) = getOrCreate(name) {
    IntegerInputField(name, label, hint, value, isReadonly, isRequired, formatter, max, min, step, validator)
}

inline fun Fields.integer(
    property: KProperty<Int?>,
    label: String = property.name,
    hint: String? = label,
    value: Int? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    formatter: NumberFormatter? = NumberFormatter(),
    max: Int? = null,
    min: Int? = null,
    step: Int = 1,
    noinline validator: ((Int?) -> Unit)? = null
) = integer(property.name, label, hint, value, isReadonly, isRequired, formatter, max, min, step, validator)