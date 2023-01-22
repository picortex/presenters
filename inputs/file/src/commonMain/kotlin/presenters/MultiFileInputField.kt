@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters

import epsilon.FileBlob
import kollections.List
import presenters.properties.Clearable
import presenters.properties.Hintable
import presenters.properties.Labeled
import presenters.properties.Mutability
import presenters.properties.Requireble
import presenters.properties.Settable
import presenters.validation.Validateable
import kotlin.js.JsExport

interface MultiFileInputField : Labeled, Hintable, Mutability, Requireble, SerializableLiveDataList<FileBlob>, Settable<List<FileBlob>>, Validateable<List<FileBlob>>, Clearable {
    fun add(file: FileBlob)
    fun addAll(files: List<FileBlob>)
    fun remove(file: FileBlob)
    fun removeAll(files: List<FileBlob> = data.value.output)
}