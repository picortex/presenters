@file:Suppress("NOTHING_TO_INLINE")

package presenters.collections

import presenters.collections.internal.SelectionManagerImpl

inline fun <T> SelectionManager(
    paginator: PaginationManager<T>
): SelectionManager<T> = SelectionManagerImpl(paginator)