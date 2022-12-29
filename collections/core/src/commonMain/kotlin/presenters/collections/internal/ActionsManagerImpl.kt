package presenters.collections.internal

import actions.Action0I1R
import kollections.List
import live.Live
import presenters.collections.ActionsManager
import presenters.collections.ActionManagerBuilder
import presenters.collections.SelectionManager

@PublishedApi
internal class ActionsManagerImpl<T>(
    private val selector: SelectionManager<T>,
    private val builder: ActionManagerBuilder<T>
) : ActionsManager<T> {
    override val actions: Live<List<Action0I1R<Unit>>> = selector.state.map {
        builder.buildActions(selector.selected)
    }
    override fun actionsOf(item: T): List<Action0I1R<Unit>> = builder.buildSingleSelectActions(item)
}