@file:Suppress("NOTHING_TO_INLINE")

package presenters.collections

import kollections.toIList
import actions.Action0
import actions.action0I1R
import kollections.List

class ActionManagerBuilder<T> {
    private var primaryActionsBuilder: MutableList<Action0<Unit>>.() -> Unit = {}
    private var singleSelectActionsBuilder: MutableList<Action0<Unit>>.(T) -> Unit = {}
    private var multiSelectActionsBuilder: (MutableList<Action0<Unit>>).(List<T>) -> Unit = {}
    private var globalSelectActionsBuilder: MutableList<Action0<Unit>>.(SelectedGlobal<T>) -> Unit = {}

    inline fun MutableList<Action0<Unit>>.on(
        name: String,
        noinline handler: () -> Unit
    ) = add(action0I1R(name, handler))

    inline fun MutableList<Action0<Unit>>.onCreate(
        noinline handler: () -> Unit
    ) = on("Create", handler)

    inline fun MutableList<Action0<Unit>>.onAdd(
        noinline handler: () -> Unit
    ) = on("Add", handler)

    inline fun MutableList<Action0<Unit>>.onAddAll(
        noinline handler: () -> Unit
    ) = on("Add all", handler)

    inline fun MutableList<Action0<Unit>>.onDelete(
        noinline handler: () -> Unit
    ) = on("Delete", handler)

    inline fun MutableList<Action0<Unit>>.onDeleteAll(
        noinline handler: () -> Unit
    ) = on("Delete all", handler)

    inline fun MutableList<Action0<Unit>>.onView(
        noinline handler: () -> Unit
    ) = on("View", handler)

    inline fun MutableList<Action0<Unit>>.onEdit(
        noinline handler: () -> Unit
    ) = on("Edit", handler)

    inline fun MutableList<Action0<Unit>>.onDuplicate(
        noinline handler: () -> Unit
    ) = on("Duplicate", handler)

    fun primary(builder: MutableList<Action0<Unit>>.() -> Unit) {
        primaryActionsBuilder = builder
    }

    fun single(builder: MutableList<Action0<Unit>>.(T) -> Unit) {
        singleSelectActionsBuilder = builder
    }

    fun multi(builder: (MutableList<Action0<Unit>>).(List<T>) -> Unit) {
        multiSelectActionsBuilder = builder
    }

    fun global(builder: MutableList<Action0<Unit>>.(SelectedGlobal<T>) -> Unit) {
        globalSelectActionsBuilder = builder
    }

    fun buildPrimaryActions() = buildList(primaryActionsBuilder)

    fun buildSingleSelectActions(selected: T) = buildList { singleSelectActionsBuilder(selected) }.toIList()

    fun buildMultiSelectActions(selected: List<T>) = buildList { multiSelectActionsBuilder(selected) }.toIList()

    fun buildGlobalSelectActions(state: SelectedGlobal<T>) = buildList { globalSelectActionsBuilder(state) }.toIList()

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