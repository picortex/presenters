package presenters.collections

import presenters.collections.internal.ActionsManagerImpl

inline fun <T> actionsOf(
    selector: SelectionManager<T>,
    builder: ActionsManagerBuilder<T>.() -> Unit
): ActionsManager<T> = ActionsManagerImpl(selector, ActionsManagerBuilder<T>().apply(builder))

inline fun <T> actionsOf(): ActionsManager<T> = ActionsManagerImpl(
    selector = SelectionManager(SinglePagePaginator()),
    builder = ActionsManagerBuilder()
)