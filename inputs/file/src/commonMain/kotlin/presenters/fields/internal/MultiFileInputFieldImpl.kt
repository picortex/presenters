package presenters.fields.internal

import epsilon.FileBlob
import epsilon.serializers.FileBlobSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ArraySerializer
import presenters.fields.InputLabel
import presenters.fields.MultiFileInputField
import presenters.fields.SingleValuedField
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

class MultiFileInputFieldImpl(
    name: String,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    label: InputLabel = InputLabel(name, isRequired),
    override val hint: String = label.text,
    defaultValue: Array<FileBlob>? = null,
    validator: ((Array<FileBlob>?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) : DirectInputValuedField<Array<FileBlob>>(name, isRequired, label, defaultValue, false, validator), MultiFileInputField {

    override val serializer: KSerializer<Array<FileBlob>> = ArraySerializer(FileBlobSerializer)

    override fun validate(value: Array<FileBlob>?): ValidationResult {
        val tag = label.capitalizedWithoutAstrix()
        if (isRequired && value == null) {
            return Invalid(IllegalArgumentException("$tag is required"))
        }

        return try {
            validator?.invoke(value)
            Valid
        } catch (err: Throwable) {
            Invalid(err)
        }
    }

    override fun add(file: FileBlob) {
        val files = data.value.output ?: arrayOf()
        set(files + file)
    }
}