package presenters.collections

import kollections.List
import kotlin.js.JsExport

@JsExport
interface Table<T> :
    Pageable<T>, PaginationManager<T>,
    Selectable<T>, SelectionManager<T>,
    Actionable<T>, ActionsManager<T> {
    val columns: List<Column<T>>
}