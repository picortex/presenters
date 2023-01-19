@file:Suppress("NOTHING_TO_INLINE")

package presenters.collections

import actions.Action0
import actions.builders.Action0I1RBuilder
import kollections.List
import kollections.toIList

class ActionsManagerBuilder<T> @PublishedApi internal constructor(
    primary: MutableList<Action0<Unit>> = mutableListOf(),
    single: MutableList<Action0<Unit>> = mutableListOf(),
    multi: MutableList<Action0<Unit>> = mutableListOf(),
    global: MutableList<Action0<Unit>> = mutableListOf(),
    @PublishedApi
    internal val filters: MutableSet<String> = mutableSetOf()
) {
    @PublishedApi
    internal val primaryActions = Action0I1RBuilder(primary)

    @PublishedApi
    internal val singleActions = Action0I1RBuilder(single)

    @PublishedApi
    internal val singleActionsContainer = mutableListOf<Action0I1RBuilder<Unit>.(T) -> Unit>()

    @PublishedApi
    internal val multiActionsBuilder = Action0I1RBuilder(multi)

    @PublishedApi
    internal val multiActionsContainer = mutableListOf<Action0I1RBuilder<Unit>.(List<T>) -> Unit>()

    @PublishedApi
    internal val globalActions = Action0I1RBuilder(global)

    @PublishedApi
    internal val globalActionsContainer = mutableListOf<Action0I1RBuilder<Unit>.(SelectedGlobal<T>) -> Unit>()

    inline fun primary(builder: Action0I1RBuilder<Unit>.() -> Unit) {
        primaryActions.apply(builder)
    }

    inline fun single(noinline builder: Action0I1RBuilder<Unit>.(T) -> Unit) {
        singleActionsContainer.add(builder)
    }

    inline fun multi(noinline builder: Action0I1RBuilder<Unit>.(List<T>) -> Unit) {
        multiActionsContainer.add(builder)
    }

    inline fun global(noinline builder: Action0I1RBuilder<Unit>.(SelectedGlobal<T>) -> Unit) {
        globalActionsContainer.add(builder)
    }

    private inline fun Collection<Action0<Unit>>.applyFilters() = associateBy {
        it.name
    }.filterKeys { !filters.contains(it.lowercase()) }.values.toIList()

    fun buildPrimaryActions() = primaryActions.actions.applyFilters()

    fun buildSingleSelectActions(selected: T) = singleActions.apply {
        singleActionsContainer.forEach { builder -> builder(selected) }
    }.actions.applyFilters()

    fun buildMultiSelectActions(selected: List<T>) = multiActionsBuilder.apply {
        multiActionsContainer.forEach { builder -> builder(selected) }
    }.actions.applyFilters()

    fun buildGlobalSelectActions(state: SelectedGlobal<T>) = globalActions.apply {
        globalActionsContainer.forEach { builder -> builder(state) }
    }.actions.applyFilters()

    fun buildActions(selected: Selected<T>) = buildList {
        addAll(buildPrimaryActions())
        when (selected) {
            is SelectedNone -> {}
            is SelectedItem -> addAll(buildSingleSelectActions(selected.row.item))
            is SelectedItems -> addAll(buildMultiSelectActions(selected.page.toIList().flatMap { (_, v) -> v }.map { it.item }.toIList()))
            is SelectedGlobal -> addAll(buildGlobalSelectActions(selected))
        }
    }.toIList()
}