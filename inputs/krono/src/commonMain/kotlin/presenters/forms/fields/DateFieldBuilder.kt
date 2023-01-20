package presenters.forms.fields

import krono.LocalDate
import presenters.Label
import presenters.fields.*
import presenters.fields.internal.DateInputFieldImpl
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun Fields.date(
    name: String,
    label: String = name,
    hint: String? = label,
    value: LocalDate? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    pattern: String = DateInputFieldImpl.DEFAULT_PATTERN,
    maxDate: LocalDate? = DateInputFieldImpl.DEFAULT_MAX_DATE,
    minDate: LocalDate? = DateInputFieldImpl.DEFAULT_MIN_DATE,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = getOrCreate(name) {
    DateInputField(
        name = name,
        label = Label(label, isRequired),
        hint = hint ?: label,
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
    label: String = name.name,
    hint: String? = label,
    value: LocalDate? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    pattern: String = DateInputFieldImpl.DEFAULT_PATTERN,
    maxDate: LocalDate? = DateInputFieldImpl.DEFAULT_MAX_DATE,
    minDate: LocalDate? = DateInputFieldImpl.DEFAULT_MIN_DATE,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = date(name.name, label, hint, value, isReadonly, isRequired, pattern, maxDate, minDate, validator)