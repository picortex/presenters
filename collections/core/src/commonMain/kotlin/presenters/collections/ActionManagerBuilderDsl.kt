package presenters.collections

import presenters.collections.internal.ActionsManagerImpl
import presenters.collections.internal.EmptyActionsManagerImpl

fun <T> actionsOf(
    selector: SelectionManager<T>,
    builder: ActionManagerBuilder<T>.() -> Unit
): ActionsManager<T> = ActionsManagerImpl(selector, ActionManagerBuilder<T>().apply(builder))

fun <T> actionsOf(): ActionsManager<T> = EmptyActionsManagerImpl