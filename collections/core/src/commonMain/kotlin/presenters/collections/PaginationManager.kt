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
    val page: Live<LazyState<Page<T>>>
    val continuous: List<Row<T>>
    val currentPageOrNull get() = page.value.data
    var capacity: Int
    fun wipeMemory()
    fun clearPages()
    fun updateLoader(loader: (no: Int, capacity: Int) -> Later<Page<@UnsafeVariance T>>)
    fun setPageCapacity(cap: Int)
    fun refresh(): Later<Any?>
    fun loadNextPage(): Later<Any?>
    fun loadPreviousPage(): Later<Any?>
    fun loadPage(no: Int): Later<Page<T>>
    fun loadFirstPage(): Later<Page<T>>
    fun loadLastPage(): Later<Page<T>>
    fun forEachPage(block: (Page<T>) -> Unit)

    @JsName("findRow")
    fun find(row: Int, page: Int): SelectedItem<T>?

    @JsName("findItem")
    fun find(item: T): SelectedItem<T>?

    @JsName("findPage")
    fun find(page: Int): Page<T>?

    companion object {
        val DEFAULT_CAPACITY = 10

        operator fun <T> invoke(
            capacity: Int = DEFAULT_CAPACITY,
            loader: (no: Int, capacity: Int) -> Later<Page<T>>
        ): PaginationManager<T> = PaginationManagerImpl(capacity = capacity, loader = loader)
    }
}