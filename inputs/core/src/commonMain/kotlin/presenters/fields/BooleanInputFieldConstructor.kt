package presenters.fields

import presenters.fields.internal.BooleanBasedValuedFieldImpl

inline fun BooleanInputField(
    name: String,
    label: String = name,
    value: Boolean? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((Boolean?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
): BooleanInputField = BooleanBasedValuedFieldImpl(
    name = name,
    label = InputLabel(label, isReadonly),
    defaultValue = value,
    isReadonly = isReadonly,
    isRequired = isRequired,
    validator = validator,
)