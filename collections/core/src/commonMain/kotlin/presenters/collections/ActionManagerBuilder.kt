package presenters.collections

import kollections.toIList
import actions.Action0I1R
import actions.action0I1R
import kollections.List

class ActionManagerBuilder<T> {
    private var primaryActionsBuilder: MutableList<Action0I1R<Unit>>.() -> Unit = {}
    private var singleSelectActionsBuilder: MutableList<Action0I1R<Unit>>.(T) -> Unit = {}
    private var multiSelectActionsBuilder: (MutableList<Action0I1R<Unit>>).(List<T>) -> Unit = {}
    private var globalSelectActionsBuilder: MutableList<Action0I1R<Unit>>.(Selected.Global<T>) -> Unit = {}

    fun MutableList<Action0I1R<Unit>>.on(name: String, handler: () -> Unit) = add(action0I1R(name, handler))

    fun MutableList<Action0I1R<Unit>>.onCreate(handler: () -> Unit) = on("Create", handler)

    fun MutableList<Action0I1R<Unit>>.onDelete(handler: () -> Unit) = on("Delete", handler)

    fun MutableList<Action0I1R<Unit>>.onDeleteAll(handler: () -> Unit) = on("Delete All", handler)

    fun MutableList<Action0I1R<Unit>>.onView(handler: () -> Unit) = on("View", handler)

    fun MutableList<Action0I1R<Unit>>.onEdit(handler: () -> Unit) = on("Edit", handler)

    fun MutableList<Action0I1R<Unit>>.onDuplicate(handler: () -> Unit) = on("Duplicate", handler)

    fun primary(builder: MutableList<Action0I1R<Unit>>.() -> Unit) {
        primaryActionsBuilder = builder
    }

    fun single(builder: MutableList<Action0I1R<Unit>>.(T) -> Unit) {
        singleSelectActionsBuilder = builder
    }

    fun multi(builder: (MutableList<Action0I1R<Unit>>).(List<T>) -> Unit) {
        multiSelectActionsBuilder = builder
    }

    fun global(builder: MutableList<Action0I1R<Unit>>.(Selected.Global<T>) -> Unit) {
        globalSelectActionsBuilder = builder
    }

    fun buildPrimaryActions() = buildList(primaryActionsBuilder)

    fun buildSingleSelectActions(selected: T) = buildList { singleSelectActionsBuilder(selected) }.toIList()

    fun buildMultiSelectActions(selected: List<T>) = buildList { multiSelectActionsBuilder(selected) }.toIList()

    fun buildGlobalSelectActions(state: Selected.Global<T>) = buildList { globalSelectActionsBuilder(state) }.toIList()

    fun buildActions(selected: Selected<T>) = buildList {
        addAll(buildPrimaryActions())
        when (selected) {
            is Selected.None -> {}
            is Selected.Item -> addAll(buildSingleSelectActions(selected.value))
            is Selected.Items -> addAll(buildMultiSelectActions(selected.values))
            is Selected.Global -> addAll(buildGlobalSelectActions(selected))
        }
    }.toIList()
}