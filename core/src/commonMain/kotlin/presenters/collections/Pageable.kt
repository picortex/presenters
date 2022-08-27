@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import kotlin.js.JsExport

@JsExport
interface Pageable<out T> {
    val paginator: PaginationManager<T>
}