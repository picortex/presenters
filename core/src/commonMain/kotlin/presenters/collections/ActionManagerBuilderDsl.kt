package presenters.collections

import presenters.collections.internal.ActionsManagerImpl

fun <T> actionsOf(
    selector: SelectionManager<T>,
    builder: ActionManagerBuilder<T>.() -> Unit
): ActionsManager<T> = ActionsManagerImpl(selector, ActionManagerBuilder<T>().apply(builder))