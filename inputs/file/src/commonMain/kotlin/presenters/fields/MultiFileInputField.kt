@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import epsilon.FileBlob
import epsilon.serializers.FileBlobSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ArraySerializer
import presenters.fields.internal.AbstractValuedField
import kotlin.js.JsExport

class MultiFileInputField(
    override val name: String,
    override val isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    val hint: String = label.text,
    override val defaultValue: Array<FileBlob>? = null,
    validator: ((Array<FileBlob>?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) : AbstractValuedField<Array<FileBlob>, Array<FileBlob>>(name, isRequired, label, defaultValue, { it }, false, validator) {

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

    fun add(file: FileBlob) {
        val files = output.value ?: arrayOf()
        set(files + file)
    }
}