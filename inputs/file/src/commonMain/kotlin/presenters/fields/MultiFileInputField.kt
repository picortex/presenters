@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import epsilon.FileBlob
import kollections.List
import live.Live
import presenters.OutputList
import kotlin.js.JsExport

interface MultiFileInputField : SingleValuedField<List<FileBlob>, List<FileBlob>> {
    override val data: Live<OutputList<FileBlob>>
    val hint: String?
    fun add(file: FileBlob)
}