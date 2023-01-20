package presenters

import presenters.internal.TextInputFieldImpl

inline fun TextInputField(
    name: String,
    label: String? = name,
    hint: String? = label,
    value: String? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    maxLength: Int? = null,
    minLength: Int? = null,
    noinline validator: ((String?) -> Unit)? = null
): TextInputField = TextInputFieldImpl(
    name = name,
    label = Label(label ?: name, isRequired),
    hint = hint ?: label ?: name,
    value = value,
    isReadonly = isReadonly,
    isRequired = isRequired,
    maxLength = maxLength,
    minLength = minLength,
    validator = validator,
)