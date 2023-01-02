package presenters.form.fields

import epsilon.FileBlob
import presenters.fields.InputLabel
import presenters.fields.MultiFileInputField
import presenters.fields.SingleFileInputField
import presenters.fields.SingleValuedField
import presenters.fields.internal.MultiFileInputFieldImpl
import presenters.forms.Fields
import presenters.forms.fields.getOrCreate
import kotlin.reflect.KProperty

fun Fields.files(
    name: String,
    label: String = name,
    hint: String? = label,
    value: Array<FileBlob>? = null,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    validator: ((Array<FileBlob>?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
): MultiFileInputField = getOrCreate(name) {
    MultiFileInputFieldImpl(
        name = name,
        label = InputLabel(label, isRequired),
        hint = hint ?: label,
        defaultValue = value,
        isRequired = isRequired,
        validator = validator
    )
}

inline fun Fields.files(
    name: KProperty<*>,
    label: String = name.name,
    hint: String? = label,
    value: Array<FileBlob>? = null,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((Array<FileBlob>?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = files(name.name, label, hint, value, isRequired, validator)