package presenters.fields

import epsilon.FileBlob
import presenters.fields.InputLabel
import presenters.fields.SingleFileInputField
import presenters.fields.SingleValuedField
import presenters.fields.internal.SingleFileInputFieldImpl
import presenters.forms.Fields
import presenters.forms.fields.getOrCreate
import kotlin.reflect.KProperty

inline fun SingleFileInputField(
    name: String,
    label: String = name,
    hint: String? = label,
    value: FileBlob? = null,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((FileBlob?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
): SingleFileInputField = SingleFileInputFieldImpl(
    name = name,
    label = InputLabel(label, isRequired),
    hint = hint ?: label,
    defaultValue = value,
    isRequired = isRequired,
    validator = validator
)