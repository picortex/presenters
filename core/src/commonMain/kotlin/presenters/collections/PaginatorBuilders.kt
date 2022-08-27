@file:Suppress("FunctionName", "NOTHING_TO_INLINE")

package presenters.collections

import koncurrent.Later
import koncurrent.SynchronousExecutor
import presenters.collections.internal.PaginationManagerImpl

inline fun <T> SinglePagePaginator(
    currentPage: Page<T> = Page()
): PaginationManager<T> = PaginationManagerImpl(capacity = currentPage.capacity) { _, _ ->
    Later.resolve(currentPage)
}

inline fun <T> SinglePagePaginator(
    items: Collection<T>
): PaginationManager<T> = SinglePagePaginator(Page(items))

fun <T> CollectionPaginator(
    collection: Collection<T>,
    capacity: Int = PaginationManager.DEFAULT_CAPACITY,
): PaginationManager<T> = PaginationManager(capacity) { no, cap ->
    Later(SynchronousExecutor) { resolve, reject ->
        try {
            val chunked = collection.chunked(cap)
            val page = if (no <= 0) {
                Page(chunked.last(), cap, chunked.size)
            } else {
                Page(chunked[no - 1], cap, no)
            }
            resolve(page)
        } catch (err: Throwable) {
            reject(err)
        }
    }
}