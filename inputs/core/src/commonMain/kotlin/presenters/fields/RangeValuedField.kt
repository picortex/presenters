@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.fields

import presenters.validation.Validateable0
import kotlin.js.JsExport

interface RangeValuedField<I, out O : Any> : ValuedField<Range<O>>, Validateable0 {
    val start: SingleValuedField<I, O>
    val end: SingleValuedField<I, O>
    val transformer: (I?) -> O?
    val limit: Range<O>?
    fun setStart(value: I?)
    fun setEnd(value: I?)
}