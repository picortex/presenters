@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters

import presenters.fields.Range
import presenters.properties.Clearable
import presenters.properties.Hintable
import presenters.properties.Labeled
import presenters.properties.Mutability
import presenters.properties.Requireble
import presenters.properties.SettableRange
import presenters.properties.Typeable
import presenters.validation.Validateable
import kotlin.js.JsExport

interface RangeInputField<O> : InputField, Labeled, Hintable, Mutability, Requireble, LiveOutputData<Range<O>>, Validateable<Range<O>>, SettableRange<O>, Typeable, Clearable {
    val start: LiveOutputData<O>
    val end: LiveOutputData<O>
    val limit: Range<O>?
}