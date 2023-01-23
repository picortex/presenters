@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import kotlin.js.JsExport

interface DataCollection<T> : Pageable<T>, PaginationManager<T>, Selectable<T>, SelectionManager<T> {

    val actions: ActionsManager<T>

    fun manageActions(block: (manager: ActionsManager<T>) -> Unit): DataCollection<T>

    override fun <R> map(transform: (T) -> R): DataCollection<R>
}