@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters

import presenters.fields.Formatter
import presenters.fields.properties.Clearable
import presenters.fields.properties.Hintable
import presenters.fields.properties.Labeled
import presenters.fields.properties.Mutability
import presenters.fields.properties.Requireble
import presenters.fields.properties.Settable
import presenters.validation.Validateable0
import presenters.validation.Validateable1
import kotlin.js.JsExport

interface TransformingInputField<I, O> : InputField, Labeled, Hintable, Mutability, Requireble, Settable<I>, LiveFormattedData<I,O>, Validateable1<O>, Validateable0, Clearable {
    val formatter: Formatter<O>?
    val transformer: (I?) -> O?
}