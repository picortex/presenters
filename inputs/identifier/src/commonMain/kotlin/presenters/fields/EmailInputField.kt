package presenters.fields

import identifier.Email
import kotlinx.serialization.builtins.serializer
import presenters.Label
import presenters.fields.internal.TextBasedValuedFieldImpl

@PublishedApi
internal fun EmailCompoundValidator(validator: ((String?) -> Unit)?): ((String?) -> Unit) = { email: String? ->
    validator?.invoke(email)
    Email(email ?: throw IllegalArgumentException("Email values can't be null"))
}

inline fun EmailInputField(
    name: String,
    label: String? = name,
    hint: String? = label,
    value: String? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    maxLength: Int? = TextBasedValuedFieldImpl.DEFAULT_MAX_LENGTH,
    minLength: Int? = TextBasedValuedFieldImpl.DEFAULT_MIN_LENGTH,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
): TextBasedValuedField<String> = TextBasedValuedFieldImpl(
    name = name,
    label = Label(label ?: name, isRequired),
    hint = hint ?: label ?: name,
    defaultValue = value,
    transformer = { it },
    serializer = String.serializer(),
    isReadonly = isReadonly,
    isRequired = isRequired,
    maxLength = maxLength,
    minLength = minLength,
    validator = EmailCompoundValidator(validator),
)