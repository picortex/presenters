package presenters.fields

import epsilon.FileBlob
import presenters.Label
import presenters.fields.internal.SingleFileInputFieldImpl

inline fun SingleFileInputField(
    name: String,
    label: String = name,
    hint: String? = label,
    value: FileBlob? = null,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((FileBlob?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
): SingleFileInputField = SingleFileInputFieldImpl(
    name = name,
    label = Label(label, isRequired),
    hint = hint ?: label,
    defaultValue = value,
    isRequired = isRequired,
    validator = validator
)