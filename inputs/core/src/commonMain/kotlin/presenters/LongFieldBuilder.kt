package presenters

import formatter.NumberFormatter
import presenters.internal.numbers.LongInputFieldImpl
import kotlin.reflect.KProperty

inline fun LongInputField(
    name: String,
    label: String = name,
    hint: String? = label,
    value: Long? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    formatter: NumberFormatter? = NumberFormatter(),
    max: Long? = null,
    min: Long? = null,
    step: Long? = 1,
    noinline validator: ((Long?) -> Unit)? = null
): NumberInputField<Long> = LongInputFieldImpl(
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

inline fun Fields.long(
    name: String,
    label: String = name,
    hint: String? = label,
    value: Long? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    formatter: NumberFormatter? = NumberFormatter(),
    max: Long? = null,
    min: Long? = null,
    step: Long? = 1,
    noinline validator: ((Long?) -> Unit)? = null
): NumberInputField<Long> = getOrCreate(name) {
    LongInputField(name, label, hint, value, isReadonly, isRequired, formatter, max, min, step, validator)
}

inline fun Fields.long(
    property: KProperty<Long?>,
    label: String = property.name,
    hint: String? = label,
    value: Long? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    formatter: NumberFormatter? = NumberFormatter(),
    max: Long? = null,
    min: Long? = null,
    step: Long? = 1,
    noinline validator: ((Long?) -> Unit)? = null
) = long(property.name, label, hint, value, isReadonly, isRequired, formatter, max, min, step, validator)