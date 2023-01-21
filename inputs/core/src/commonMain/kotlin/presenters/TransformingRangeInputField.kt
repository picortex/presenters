@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters

import presenters.properties.Clearable
import presenters.properties.Hintable
import presenters.properties.Labeled
import presenters.properties.Mutability
import presenters.properties.Requireble
import presenters.properties.SettableRange
import presenters.validation.Validateable
import kotlin.js.JsExport

interface TransformingRangeInputField<I, O> : InputField, Labeled, Hintable, Mutability, Requireble, SettableRange<I>, LiveFormattedData<I, Range<O>>, Validateable<Range<O>>, Clearable {
    val start: LiveFormattedData<I, O>
    val end: LiveFormattedData<I, O>
    val transformer: (I?) -> O?
}