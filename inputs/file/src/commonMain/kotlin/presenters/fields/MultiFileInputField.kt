@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import epsilon.FileBlob
import kotlin.js.JsExport

interface MultiFileInputField : SingleValuedField<Array<FileBlob>, Array<FileBlob>> {
    val hint: String?
    fun add(file: FileBlob)
}