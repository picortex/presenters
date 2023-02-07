@file:Suppress("NOTHING_TO_INLINE")

package presenters.collections

import actions.Action0
import actions.builders.Actions0Builder
import kollections.List
import kollections.toIList

class CollectionActionsBuilder<T> @PublishedApi internal constructor(
    primary: MutableList<Action0<Unit>> = mutableListOf(),
    single: MutableList<Action0<Unit>> = mutableListOf(),
    multi: MutableList<Action0<Unit>> = mutableListOf(),
    global: MutableList<Action0<Unit>> = mutableListOf(),
    @PublishedApi
    internal val filters: MutableSet<String> = mutableSetOf()
) {
    @PublishedApi
    internal val primaryActions = Actions0Builder(primary)

    @PublishedApi
    internal val singleActions = Actions0Builder(single)

    @PublishedApi
    internal val singleActionsContainer = mutableListOf<Actions0Builder<Unit>.(T) -> Unit>()

    @PublishedApi
    internal val multiActionsBuilder = Actions0Builder(multi)

    @PublishedApi
    internal val multiActionsContainer = mutableListOf<Actions0Builder<Unit>.(List<T>) -> Unit>()

    @PublishedApi
    internal val globalActions = Actions0Builder(global)

    @PublishedApi
    internal val globalActionsContainer = mutableListOf<Actions0Builder<Unit>.(SelectedGlobal<T>) -> Unit>()

    inline fun primary(builder: Actions0Builder<Unit>.() -> Unit) {
        primaryActions.apply(builder)
    }

    inline fun single(noinline builder: Actions0Builder<Unit>.(T) -> Unit) {
        singleActionsContainer.add(builder)
    }

    inline fun multi(noinline builder: Actions0Builder<Unit>.(List<T>) -> Unit) {
        multiActionsContainer.add(builder)
    }

    inline fun global(noinline builder: Actions0Builder<Unit>.(SelectedGlobal<T>) -> Unit) {
        globalActionsContainer.add(builder)
    }

    private inline fun Collection<Action0<Unit>>.applyFilters() = associateBy {
        it.key
    }.filterKeys {
        !filters.contains(it.lowercase())
    }.values.toIList()

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