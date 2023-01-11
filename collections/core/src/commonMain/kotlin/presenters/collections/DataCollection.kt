@file:JsExport

package presenters.collections

import kotlin.js.JsExport

interface DataCollection<T> :
    Pageable<T>, PaginationManager<T>,
    Selectable<T>, SelectionManager<T>,
    Actionable<T>, ActionsManager<T>