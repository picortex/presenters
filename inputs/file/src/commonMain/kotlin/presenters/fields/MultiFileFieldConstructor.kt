package presenters.fields

import epsilon.FileBlob
import presenters.fields.internal.MultiFileInputFieldImpl
import presenters.forms.Fields
import presenters.forms.fields.getOrCreate
import kotlin.reflect.KProperty

inline fun MultiFileInputField(
    name: String,
    label: String = name,
    hint: String? = label,
    value: Array<FileBlob>? = null,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((Array<FileBlob>?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
): MultiFileInputField = MultiFileInputFieldImpl(
    name = name,
    label = InputLabel(label, isRequired),
    hint = hint ?: label,
    defaultValue = value,
    isRequired = isRequired,
    validator = validator
)