package presenters.collections

import kollections.Set
import kollections.toISet
import live.MutableLiveSet
import live.mutableLiveSetOf

class ColumnsBuilder<D> @PublishedApi internal constructor(internal val columns: MutableSet<Column<D>>) {

    internal val current: MutableLiveSet<Column<D>> = mutableLiveSetOf()

    internal val hidden = mutableSetOf<String>()
    internal val removed = mutableSetOf<String>()

    fun hide(name: String) = update { hidden.add(name) }

    fun show(name: String) = update { hidden.remove(name) }

    fun find(name: String): Column<D>? = columns.find { it.name.contentEquals(name, ignoreCase = true) }

    fun remove(name: String) = update {
        val col = find(name) ?: return@update
        columns.remove(col)
    }

    fun selectable(name: String = "Select") = update {
        columns.add(Column.Select(name))
    }

    fun column(name: String, accessor: (Row<D>) -> String) = update {
        columns.add(Column.Data(name, accessor))
    }

    fun action(name: String) = update {
        columns.add(Column.Action(name))
    }

    fun all(includingRemoved: Boolean): Set<Column<D>> = if (includingRemoved) {
        columns.toISet()
    } else {
        columns.filter { !removed.contains(it.name) }.toISet()
    }

    private fun update(block: () -> Unit) {
        block()
        current.value = all(includingRemoved = false).filter { !hidden.contains(it.name) }.toISet()
    }
}