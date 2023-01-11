package presenters.fields

import krono.LocalDate
import presenters.fields.DateRangeInputField
import presenters.fields.Range
import presenters.fields.InputLabel
import presenters.fields.internal.DateRangeInputFieldImpl
import presenters.forms.Fields
import kotlin.reflect.KProperty

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
    label = InputLabel(label, isRequired),
    limit = limit,
    defaultStart = value?.start,
    defaultEnd = value?.end,
    isReadonly = isReadonly,
    isRequired = isRequired,
    validator = validator,
)