@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters

import epsilon.FileBlob
import presenters.properties.Clearable
import presenters.properties.Hintable
import presenters.properties.Labeled
import presenters.properties.Mutability
import presenters.properties.Requireble
import presenters.properties.Settable
import presenters.validation.Validateable
import kotlin.js.JsExport

interface SingleFileInputField : Labeled, Hintable, Mutability, Requireble, SerializableLiveData<FileBlob>, Settable<FileBlob>, Validateable<FileBlob>, Clearable