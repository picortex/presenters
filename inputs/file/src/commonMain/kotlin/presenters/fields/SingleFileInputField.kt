@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import epsilon.FileBlob
import kotlin.js.JsExport

interface SingleFileInputField : SingleValuedField<FileBlob, FileBlob> {
    val hint: String?
}