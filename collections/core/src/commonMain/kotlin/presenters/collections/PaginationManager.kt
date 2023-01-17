@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.collections

import koncurrent.Later
import kollections.List
import live.Live
import presenters.collections.internal.PaginationManagerImpl
import kase.LazyState
import kotlin.js.JsExport

interface PaginationManager<out T> {
    val page: Live<LazyState<Page<T>>>
    val continuous: List<Row<T>>
    val currentPageOrNull get() = page.value.data
    var capacity: Int
    fun readPageFromMemoryOrEmpty(page: Int, cap: Int): Page<T>
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
    fun findRow(page: Int, row: Int): SelectedItem<T>?

    companion object {
        val DEFAULT_CAPACITY = 10

        operator fun <T> invoke(
            capacity: Int = DEFAULT_CAPACITY,
            loader: (no: Int, capacity: Int) -> Later<Page<T>>
        ): PaginationManager<T> = PaginationManagerImpl(capacity = capacity, loader = loader)
    }
}