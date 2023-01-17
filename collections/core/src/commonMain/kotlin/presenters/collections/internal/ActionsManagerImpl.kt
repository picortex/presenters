package presenters.collections.internal

import actions.Action0
import kollections.List
import live.Live
import presenters.collections.ActionsManager
import presenters.collections.ActionManagerBuilder
import presenters.collections.SelectionManager

@PublishedApi
internal class ActionsManagerImpl<T>(
    selector: SelectionManager<T>,
    private val builder: ActionManagerBuilder<T>
) : ActionsManager<T> {
    override val actions: Live<List<Action0<Unit>>> = selector.selected.map {
        builder.buildActions(it)
    }

    override fun actionsOf(item: T): List<Action0<Unit>> = builder.buildSingleSelectActions(item)
}