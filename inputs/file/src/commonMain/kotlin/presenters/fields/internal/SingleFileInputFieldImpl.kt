package presenters.fields.internal

import epsilon.FileBlob
import epsilon.serializers.FileBlobSerializer
import kotlinx.serialization.KSerializer
import presenters.Label
import presenters.fields.SingleFileInputField
import presenters.fields.SingleValuedField
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

@PublishedApi
internal class SingleFileInputFieldImpl(
    name: String,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    label: Label = Label(name, isRequired),
    override val hint: String = label.text,
    defaultValue: FileBlob? = null,
    validator: ((FileBlob?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) : DirectInputValuedField<FileBlob>(name, isRequired, label, defaultValue, false, validator), SingleFileInputField {

    override val serializer: KSerializer<FileBlob> = FileBlobSerializer

    override fun validate(value: FileBlob?): ValidationResult {
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
}