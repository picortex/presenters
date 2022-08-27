@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.collections

import koncurrent.Later
import live.Live
import presenters.collections.internal.PaginationManagerImpl
import kotlin.js.JsExport

@JsExport
interface PaginationManager<out T> {
    val live: Live<PageableState<T>>
    val currentPageOrNull get() = live.value.currentPageOrNull
    var capacity: Int
    fun readPageFromMemory(page: Int, cap: Int): Page<T>
    fun readPageFromMemoryOrNull(page: Int, cap: Int): Page<T>?
    fun writePageToMemory(page: Page<@UnsafeVariance T>): Page<T>?
    fun setPageCapacity(cap: Int)
    fun refresh(): Later<out Page<T>>
    fun loadNextPage(): Later<out Page<T>>
    fun loadPreviousPage(): Later<out Page<T>>
    fun loadPage(no: Int): Later<out Page<T>>
    fun loadFirstPage(): Later<out Page<T>>
    fun loadLastPage(): Later<out Page<T>>

    companion object {
        val DEFAULT_CAPACITY = 10

        operator fun <T> invoke(
            capacity: Int = DEFAULT_CAPACITY,
            onPage: (no: Int, capacity: Int) -> Later<out Page<T>>
        ): PaginationManager<T> = PaginationManagerImpl(capacity = capacity, onPage = onPage)
    }
}