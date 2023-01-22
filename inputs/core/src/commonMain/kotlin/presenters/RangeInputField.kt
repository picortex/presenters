@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters

import presenters.properties.SettableRange
import presenters.validation.Validateable
import kotlin.js.JsExport

interface RangeInputField<O> : InputField, CommonInputProperties, SerializableLiveData<Range<O>>, Validateable<Range<O>>, SettableRange<O> {
    val start: SerializableLiveData<O>
    val end: SerializableLiveData<O>
}