package presenters.collections.internal

import kotlinx.collections.interoperable.List
import presenters.actions.SimpleAction
import presenters.collections.ActionsManager
import presenters.collections.ActionManagerBuilder
import presenters.collections.SelectionManager

class ActionsManagerImpl<T>(
    private val selector: SelectionManager<T>,
    private val builder: ActionManagerBuilder<T>
) : ActionsManager<T> {
    override val actions: List<SimpleAction> get() = builder.buildActions(selector.selected)
    override fun actionsOf(item: T): List<SimpleAction> = builder.buildSingleSelectActions(item)
}