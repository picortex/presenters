@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.fields

import kotlin.js.JsExport

@JsExport
interface RangeValuedField<T : Comparable<T>> : SingleValuedField<Range<T>> {
    val limit: Range<T>?
    val start: T?
    val end: T?

    companion object {
        val DEFAULT_START_VALUE: Nothing? = null
        val DEFAULT_END_VALUE: Nothing? = null
        val DEFAULT_MIN: Nothing? = null
        val DEFAULT_MAX: Nothing? = null
        val DEFAULT_LIMIT: Range<Nothing>? = null
    }
}