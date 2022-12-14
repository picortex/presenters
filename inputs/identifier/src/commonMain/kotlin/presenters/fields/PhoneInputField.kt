package presenters.fields

import identifier.Phone
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import presenters.fields.internal.TextBasedValuedFieldImpl

@PublishedApi
internal fun PhoneCompoundValidator(validator: ((String?) -> Unit)?): ((String?) -> Unit)? = {
    validator?.invoke(it)
    Phone(it ?: throw IllegalArgumentException("Phone values can't be null"))
}

inline fun PhoneInputField(
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
    label = InputLabel(label ?: name, isRequired),
    hint = hint ?: label ?: name,
    defaultValue = value,
    transformer = { it },
    serializer = String.serializer(),
    isReadonly = isReadonly,
    isRequired = isRequired,
    maxLength = maxLength,
    minLength = minLength,
    validator = PhoneCompoundValidator(validator),
)