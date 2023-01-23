package presenters.collections

import kollections.Set
import kollections.toISet
import live.MutableLiveSet
import live.mutableLiveSetOf

class ColumnsBuilder<D> @PublishedApi internal constructor(internal val columns: MutableSet<Column<D>>) {

    internal val current: MutableLiveSet<Column<D>> = mutableLiveSetOf()

    internal val hidden = mutableSetOf<String>()
    internal val removed = mutableSetOf<String>()

    fun hide(name: String) {
        val n = name.lowercase()
        if (!columns.contains(n)) return
        if (hidden.contains(n)) return
        update { hidden.add(n) }
    }

    fun show(name: String) {
        val n = name.lowercase()
        if (!columns.contains(n)) return
        if (!hidden.contains(n)) return
        update { hidden.remove(n) }
    }

    fun remove(name: String) {
        val n = name.lowercase()
        if (!columns.contains(n)) return
        if (removed.contains(n)) return
        update { removed.add(n) }
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
        columns.filter { !removed.contains(it.name.lowercase()) }.toISet()
    }

    private fun update(block: () -> Unit) {
        block()
        current.value = all(includingRemoved = false).filter { !hidden.contains(it.name.lowercase()) }.toISet()
    }

    private fun Collection<Column<D>>.contains(name: String) = any {
        it.name.contentEquals(name, ignoreCase = true)
    }
}