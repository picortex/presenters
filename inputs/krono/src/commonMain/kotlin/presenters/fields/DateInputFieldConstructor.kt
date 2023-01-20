package presenters.fields

import krono.LocalDate
import presenters.Label
import presenters.fields.internal.DateInputFieldImpl

inline fun DateInputField(
    name: String,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    label: Label = Label(name, isRequired),
    hint: String = label.text,
    defaultValue: String? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    pattern: String = DateInputFieldImpl.DEFAULT_PATTERN,
    maxDate: LocalDate? = DateInputFieldImpl.DEFAULT_MAX_DATE,
    minDate: LocalDate? = DateInputFieldImpl.DEFAULT_MIN_DATE,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) : DateInputField = DateInputFieldImpl(name, isRequired, label, hint, defaultValue, isReadonly, pattern, maxDate, minDate, validator)