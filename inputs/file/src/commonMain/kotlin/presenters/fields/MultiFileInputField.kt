@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import epsilon.FileBlob
import epsilon.serializers.FileBlobSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ArraySerializer
import presenters.fields.internal.AbstractValuedField
import presenters.fields.internal.DirectInputValuedField
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult
import kotlin.js.JsExport

interface MultiFileInputField : SingleValuedField<Array<FileBlob>, Array<FileBlob>> {
    val hint: String?
    fun add(file: FileBlob)
}