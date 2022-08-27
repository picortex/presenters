@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import kotlin.js.JsExport

@JsExport
interface Selectable<in T> {
    val selector: SelectionManager<T>
}