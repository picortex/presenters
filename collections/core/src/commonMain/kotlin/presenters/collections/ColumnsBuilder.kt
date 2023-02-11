package presenters.collections

import kollections.Set
import kollections.toISet
import live.MutableLiveSet
import live.mutableLiveSetOf

class ColumnsBuilder<D> @PublishedApi internal constructor(internal val columns: MutableMap<String, Column<D>>) {
    constructor(columns: Collection<Column<D>>) : this(columns.associateBy { it.key }.toMutableMap())
    constructor() : this(mutableMapOf())

    internal val current: MutableLiveSet<Column<D>> = mutableLiveSetOf()
    private val hidden = mutableSetOf<String>()
    private val removed = mutableSetOf<String>()

    fun hide(name: String) {
        val col = find(name) ?: return
        if (hidden.contains(col.key.lowercase())) return
        update { hidden.add(col.key.lowercase()) }
    }

    fun show(name: String) {
        val col = find(name) ?: return
        if (!hidden.contains(col.key.lowercase())) return
        update { hidden.remove(col.key.lowercase()) }
    }

    fun remove(name: String) {
        val col = find(name) ?: return
        if (removed.contains(col.key.lowercase())) return
        update { removed.add(col.key.lowercase()) }
    }

    fun index(name: String, idx: Int) = set(find(name)?.copy(index = idx))

    fun selectable(name: String = "Select", key: String = name) {
        set(Column.Select(name, key, columns.size))
    }

    fun column(name: String, key: String = name, default: String = "N/A", accessor: (Row<D>) -> Any?) {
        set(Column.Data(name, key, columns.size, default, accessor))
    }

    fun rename(prev: String, curr: String) = set(find(prev)?.copy(curr))

    fun action(name: String, key: String) = set(Column.Action(name, key, columns.size))

    private fun set(col: Column<D>?) {
        if (col == null) return
        update { columns[col.key.lowercase()] = col }
    }

    fun all(includingRemoved: Boolean): Set<Column<D>> = if (includingRemoved) {
        columns.values
    } else {
        columns.values.filter { !removed.contains(it.name.lowercase()) }
    }.sortedBy {
        it.index
    }.toISet()

    private fun find(name: String) = all(includingRemoved = true).find { it.name == name }

    private fun update(block: () -> Unit) {
        block()
        current.value = all(includingRemoved = false).filter { !hidden.contains(it.name.lowercase()) }.toISet()
    }
}