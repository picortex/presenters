package presenters.forms.fields

import krono.LocalDate
import presenters.fields.*
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun Fields.date(
    name: String? = null,
    label: String? = name,
    hint: String? = label,
    value: LocalDate? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    pattern: String = DateInputField.DEFAULT_PATTERN,
    maxDate: LocalDate? = DateInputField.DEFAULT_MAX_DATE,
    minDate: LocalDate? = DateInputField.DEFAULT_MIN_DATE,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = getOrCreate { property ->
    DateInputField(
        name = name ?: property.name,
        label = InputLabel(label ?: property.name, isRequired),
        hint = hint ?: property.name,
        defaultValue = value?.toIsoString(),
        isReadonly = isReadonly,
        isRequired = isRequired,
        pattern = pattern,
        maxDate = maxDate,
        minDate = minDate,
        validator = validator
    )
}

inline fun Fields.date(
    name: KProperty<*>,
    label: String? = name.name,
    hint: String? = label,
    value: LocalDate? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    pattern: String = DateInputField.DEFAULT_PATTERN,
    maxDate: LocalDate? = DateInputField.DEFAULT_MAX_DATE,
    minDate: LocalDate? = DateInputField.DEFAULT_MIN_DATE,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = date(name.name, label, hint, value, isReadonly, isRequired, pattern, maxDate, minDate, validator)