@file:Suppress("NOTHING_TO_INLINE")

package presenters.collections

import presenters.collections.internal.ColumnsManagerImpl
import presenters.collections.internal.DataCollectionImpl

inline fun <T> scrollableListOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actionsManager: ActionsManager<T>
): ScrollableList<T> = DataCollectionImpl(
    paginator, selector, actionsManager,
    ColumnsManagerImpl(mutableSetOf())
)

inline fun <T> scrollableListOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>
): ScrollableList<T> = DataCollectionImpl(
    paginator, selector, actionsOf(selector) {},
    ColumnsManagerImpl(mutableSetOf())
)