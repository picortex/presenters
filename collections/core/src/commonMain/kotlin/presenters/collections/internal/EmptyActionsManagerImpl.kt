package presenters.collections.internal

import actions.Action0
import kollections.List
import kollections.iEmptyList
import kollections.iListOf
import live.Live
import live.liveOf
import presenters.collections.ActionsManager

@PublishedApi
internal object EmptyActionsManagerImpl : ActionsManager<Any?> {
    override val actions: Live<List<Action0<Unit>>> = liveOf(iListOf())
    override fun actionsOf(item: Any?): List<Action0<Unit>> = iEmptyList()
}