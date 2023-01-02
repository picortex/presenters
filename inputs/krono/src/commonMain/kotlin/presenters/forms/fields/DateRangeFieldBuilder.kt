package presenters.forms.fields

import krono.LocalDate
import presenters.fields.*
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun Fields.dateRange(
    name: String,
    label: String = name,
    value: Range<LocalDate>? = SingleValuedField.DEFAULT_VALUE,
    limit: Range<LocalDate>? = null,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((LocalDate?, LocalDate?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = getOrCreate(name) {
    DateRangeInputField(
        name = name,
        label = InputLabel(label, isRequired),
        limit = limit,
        defaultStart = value?.start,
        defaultEnd = value?.end,
        isReadonly = isReadonly,
        isRequired = isRequired,
        validator = validator,
    )
}

inline fun Fields.dateRange(
    name: KProperty<*>,
    label: String = name.name,
    value: Range<LocalDate>? = SingleValuedField.DEFAULT_VALUE,
    limit: Range<LocalDate>? = null,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((LocalDate?, LocalDate?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = dateRange(name.name, label, value, limit, isReadonly, isRequired, validator)