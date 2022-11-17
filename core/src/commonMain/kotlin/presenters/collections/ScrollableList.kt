@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import kollections.List
import kotlin.js.JsExport

@JsExport
interface ScrollableList<T> :
    Pageable<T>, PaginationManager<T>,
    Selectable<T>, SelectionManager<T>,
    Actionable<T>, ActionsManager<T> {
    val rows: List<Row<T>>
}