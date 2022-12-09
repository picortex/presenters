package presenters.forms.fields

import krono.LocalDate
import presenters.fields.*
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun Fields.dateRange(
    name: String? = null,
    label: String? = name,
    value: Range<LocalDate>? = SingleValuedField.DEFAULT_VALUE,
    limit: Range<LocalDate>? = null,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((String?, String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = getOrCreate { property ->
    DateRangeInputField(
        name = name ?: property.name,
        label = InputLabel(label ?: property.name, isRequired),
        limit = limit,
        isReadonly = isReadonly,
        isRequired = isRequired,
        validator = validator,
    )
}

inline fun Fields.dateRange(
    name: KProperty<*>,
    label: String? = name.name,
    value: Range<LocalDate>? = SingleValuedField.DEFAULT_VALUE,
    limit: Range<LocalDate>? = null,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((String?, String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = dateRange(name.name, label, value, limit, isReadonly, isRequired, validator)