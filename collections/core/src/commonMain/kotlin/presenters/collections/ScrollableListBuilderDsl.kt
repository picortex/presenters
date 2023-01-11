package presenters.collections

import kollections.iEmptyList
import presenters.collections.internal.DataCollectionImpl

inline fun <T> scrollableListOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actionsManager: ActionsManager<T>
): ScrollableList<T> = DataCollectionImpl(paginator, selector, actionsManager, iEmptyList())