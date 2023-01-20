package presenters.fields.internal

import epsilon.FileBlob
import epsilon.serializers.FileBlobSerializer
import kollections.List
import kollections.serializers.ListSerializer
import kollections.toIList
import kotlinx.serialization.KSerializer
import presenters.Label
import presenters.fields.MultiFileInputField
import presenters.fields.SingleValuedField
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

@PublishedApi
internal class MultiFileInputFieldImpl(
    name: String,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    label: Label = Label(name, isRequired),
    override val hint: String = label.text,
    defaultValue: List<FileBlob>? = null,
    validator: ((List<FileBlob>?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) : DirectInputValuedField<List<FileBlob>>(name, isRequired, label, defaultValue, false, validator), MultiFileInputField {

    override val serializer: KSerializer<List<FileBlob>> = ListSerializer(FileBlobSerializer)

    override fun validate(value: List<FileBlob>?): ValidationResult {
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
        val files = data.value.output
        set((files + file).toIList())
    }
}