@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import koncurrent.Later
import kotlinx.collections.interoperable.List
import live.Live
import kotlin.js.JsExport

@JsExport
interface ScrollableList<T> :
    Pageable<T>, PaginationManager<T>,
    Selectable<T>, SelectionManager<T>,
    Actionable<T>, ActionsManager<T> {
    val items: Live<List<Row<T>>>
}