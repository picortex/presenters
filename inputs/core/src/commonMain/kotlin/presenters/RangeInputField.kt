@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters

import presenters.fields.Range
import presenters.properties.Clearable
import presenters.properties.Hintable
import presenters.properties.Labeled
import presenters.properties.Mutability
import presenters.properties.Requireble
import presenters.properties.Settable
import presenters.properties.SettableRange
import presenters.properties.Typeable
import presenters.validation.Validateable0
import presenters.validation.Validateable1
import kotlin.js.JsExport

interface RangeInputField<O> : InputField, Labeled, Hintable, Mutability, Requireble, LiveOutputData<Range<O>>, Validateable1<Range<O>>, SettableRange<O>, Validateable0, Typeable, Clearable {
    val start: LiveOutputData<O>
    val end: LiveOutputData<O>
    val limit: Range<O>?
}