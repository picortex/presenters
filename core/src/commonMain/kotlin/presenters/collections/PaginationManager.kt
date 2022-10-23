@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.collections

import koncurrent.Later
import kotlinx.collections.interoperable.List
import live.Live
import presenters.collections.internal.PaginationManagerImpl
import kotlin.js.JsExport

interface PaginationManager<out T> {
    val live: Live<PageableState<T>>
    val continuous: List<Row<T>>
    val currentPageOrNull get() = live.value.currentPageOrNull
    var capacity: Int
    fun readPageFromMemory(page: Int, cap: Int): Page<T>
    fun readPageFromMemoryOrNull(page: Int, cap: Int): Page<T>?
    fun writePageToMemory(page: Page<@UnsafeVariance T>): Page<T>?
    fun wipeMemory()
    fun clearPages()
    fun updateLoader(loader: (no: Int, capacity: Int) -> Later<Page<@UnsafeVariance T>>)
    fun setPageCapacity(cap: Int)
    fun refresh(): Later<Page<T>>
    fun loadNextPage(): Later<Page<T>>
    fun loadPreviousPage(): Later<Page<T>>
    fun loadPage(no: Int): Later<Page<T>>
    fun loadFirstPage(): Later<Page<T>>
    fun loadLastPage(): Later<Page<T>>
    fun forEachPage(block: (Page<T>) -> Unit)

    companion object {
        val DEFAULT_CAPACITY = 10

        operator fun <T> invoke(
            capacity: Int = DEFAULT_CAPACITY,
            loader: (no: Int, capacity: Int) -> Later<Page<T>>
        ): PaginationManager<T> = PaginationManagerImpl(capacity = capacity, loader = loader)
    }
}