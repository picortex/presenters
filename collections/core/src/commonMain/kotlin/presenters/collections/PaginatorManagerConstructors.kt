@file:Suppress("FunctionName", "NOTHING_TO_INLINE")

package presenters.collections

import koncurrent.Later
import kollections.toIList
import koncurrent.SynchronousExecutor
import presenters.collections.internal.PaginationManagerImpl

inline fun <T> PaginationManager(
    capacity: Int = PaginationManagerImpl.DEFAULT_CAPACITY,
    noinline loader: (no: Int, capacity: Int) -> Later<Page<T>>
): PaginationManager<T> = PaginationManagerImpl(capacity = capacity, loader = loader)

inline fun <T> SinglePagePaginator(
    currentPage: Page<T> = Page()
): PaginationManager<T> = PaginationManagerImpl(capacity = currentPage.capacity) { _, _ ->
    Later(currentPage)
}

inline fun <T> SinglePagePaginator(
    items: Collection<T>,
): PaginationManager<T> = SinglePagePaginator(Page(items.toIList()))

fun <T> CollectionPaginator(
    collection: Collection<T>,
    capacity: Int = PaginationManagerImpl.DEFAULT_CAPACITY,
): PaginationManager<T> = PaginationManager(capacity) { no, cap ->
    Later(SynchronousExecutor) { resolve, reject ->
        try {
            val chunked = collection.chunked(cap)
            val page = if (no <= 0) {
                Page(chunked.last().toIList(), cap, chunked.size)
            } else {
                Page(chunked[no - 1].toIList(), cap, no)
            }
            resolve(page)
        } catch (err: Throwable) {
            reject(err)
        }
    }
}

inline fun <T> PageLoader(noinline loader: (no: Int, capacity: Int) -> Later<T>) = loader