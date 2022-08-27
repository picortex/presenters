package presenters.forms.fields

import krono.LocalDate
import presenters.fields.*
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun Fields.dateRange(
    name: String? = null,
    label: String? = name,
    value: Range<LocalDate>? = ValuedField.DEFAULT_VALUE,
    limit: Range<LocalDate>? = RangeValuedField.DEFAULT_LIMIT,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((Range<LocalDate>?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) = getOrCreate { property ->
    DateRangeInputField(
        name = name ?: property.name,
        label = label ?: property.name,
        defaultValue = value,
        limit = limit,
        isReadonly = isReadonly,
        isRequired = isRequired,
        validator = validator,
    )
}

inline fun Fields.dateRange(
    name: KProperty<*>,
    label: String? = name.name,
    value: Range<LocalDate>? = ValuedField.DEFAULT_VALUE,
    limit: Range<LocalDate>? = RangeValuedField.DEFAULT_LIMIT,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((Range<LocalDate>?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) = dateRange(name.name, label, value, limit, isReadonly, isRequired, validator)