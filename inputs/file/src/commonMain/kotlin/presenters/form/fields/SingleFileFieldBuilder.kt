package presenters.form.fields

import epsilon.FileBlob
import presenters.fields.InputLabel
import presenters.fields.SingleFileInputField
import presenters.fields.SingleValuedField
import presenters.forms.Fields
import presenters.forms.fields.getOrCreate
import kotlin.reflect.KProperty

fun Fields.file(
    name: String,
    label: String = name,
    hint: String? = label,
    value: FileBlob? = null,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    validator: ((FileBlob?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = getOrCreate(name) {
    SingleFileInputField(
        name = name,
        label = InputLabel(label, isRequired),
        hint = hint ?: label,
        defaultValue = value,
        isRequired = isRequired,
        validator = validator
    )
}

inline fun Fields.file(
    name: KProperty<*>,
    label: String = name.name,
    hint: String? = label,
    value: FileBlob? = null,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((FileBlob?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = file(name.name, label, hint, value, isRequired, validator)