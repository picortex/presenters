@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import kollections.List
import kotlin.js.JsExport

interface ScrollableList<T> : DataCollection<T> {
    val rows: List<Row<T>>
    override fun manageActions(block: (ActionsManager<T>) -> Unit): ScrollableList<T>

    override fun <R> map(transform: (T) -> R): ScrollableList<R>
}