package presenters.fields

import krono.LocalDate
import presenters.Label
import presenters.fields.internal.DateRangeInputFieldImpl

inline fun DateRangeInputField(
    name: String,
    label: String = name,
    value: Range<LocalDate>? = null,
    limit: Range<LocalDate>? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((LocalDate?, LocalDate?) -> Unit)? = null
): DateRangeInputField = DateRangeInputFieldImpl(
    name = name,
    label = Label(label, isRequired),
    limit = limit,
    defaultStart = value?.start,
    defaultEnd = value?.end,
    isReadonly = isReadonly,
    isRequired = isRequired,
    validator = validator,
)