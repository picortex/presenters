@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.collections

import koncurrent.Later
import kollections.List
import live.Live
import presenters.collections.internal.PaginationManagerImpl
import kase.LazyState
import kotlin.js.JsExport
import kotlin.js.JsName

interface PaginationManager<T> {
    val current: Live<LazyState<Page<T>>>
    val continuous: List<Row<T>>
    val currentPageOrNull get() = current.value.data
    val capacity: Int
    fun wipeMemory()
    fun clearPages()
    fun setPageCapacity(cap: Int)
    fun refresh(): Later<Any?>
    fun loadNextPage(): Later<Any?>
    fun loadPreviousPage(): Later<Any?>
    fun loadPage(no: Int): Later<Page<T>>
    fun loadFirstPage(): Later<Page<T>>
    fun loadLastPage(): Later<Page<T>>
    fun forEachPage(block: (Page<T>) -> Unit)

    // ---------------------- finders -----------------------
    @JsName("findRow")
    fun find(row: Int, page: Int): SelectedItem<T>?

    @JsName("findItem")
    fun find(item: T): SelectedItem<T>?

    @JsName("findPage")
    fun find(page: Int): Page<T>?

    fun <R> map(transform: (T) -> R): PaginationManager<R>
}