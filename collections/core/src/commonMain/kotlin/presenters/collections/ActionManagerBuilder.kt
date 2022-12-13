package presenters.collections

import kollections.toIList
import presenters.actions.SimpleAction
import kollections.List

class ActionManagerBuilder<T> {
    private var primaryActionsBuilder: MutableList<SimpleAction>.() -> Unit = {}
    private var singleSelectActionsBuilder: MutableList<SimpleAction>.(T) -> Unit = {}
    private var multiSelectActionsBuilder: (MutableList<SimpleAction>).(List<T>) -> Unit = {}
    private var globalSelectActionsBuilder: MutableList<SimpleAction>.(Selected.Global<T>) -> Unit = {}

    fun MutableList<SimpleAction>.on(name: String, handler: () -> Unit) = add(SimpleAction(name, handler))

    fun MutableList<SimpleAction>.onCreate(handler: () -> Unit) = on("Create", handler)

    fun MutableList<SimpleAction>.onDelete(handler: () -> Unit) = on("Delete", handler)

    fun MutableList<SimpleAction>.onDeleteAll(handler: () -> Unit) = on("Delete All", handler)

    fun MutableList<SimpleAction>.onView(handler: () -> Unit) = on("View", handler)

    fun MutableList<SimpleAction>.onEdit(handler: () -> Unit) = on("Edit", handler)

    fun MutableList<SimpleAction>.onDuplicate(handler: () -> Unit) = on("Duplicate", handler)

    fun primary(builder: MutableList<SimpleAction>.() -> Unit) {
        primaryActionsBuilder = builder
    }

    fun single(builder: MutableList<SimpleAction>.(T) -> Unit) {
        singleSelectActionsBuilder = builder
    }

    fun multi(builder: (MutableList<SimpleAction>).(List<T>) -> Unit) {
        multiSelectActionsBuilder = builder
    }

    fun global(builder: MutableList<SimpleAction>.(Selected.Global<T>) -> Unit) {
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