package presenters

import presenters.internal.BooleanInputFieldImpl

inline fun BooleanInputField(
    name: String,
    label: String = name,
    value: Boolean? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((Boolean?) -> Unit)? = null
): BooleanInputField = BooleanInputFieldImpl(
    name = name,
    label = Label(label, isReadonly),
    value = value,
    isReadonly = isReadonly,
    isRequired = isRequired,
    validator = validator,
)