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

    fun selectable(name: String = "Select", key: String = name) = update {
        columns[key.lowercase()] = Column.Select(name, key)
    }

    fun column(name: String, key: String = name, default: String = "N/A", accessor: (Row<D>) -> Any?) = update {
        columns[key.lowercase()] = Column.Data(name, key, default, accessor)
    }

    fun action(name: String, key: String) = update {
        columns[key.lowercase()] = Column.Action(name, key)
    }

    fun all(includingRemoved: Boolean): Set<Column<D>> = if (includingRemoved) {
        columns.values.toISet()
    } else {
        columns.values.filter { !removed.contains(it.name.lowercase()) }.toISet()
    }

    private fun update(block: () -> Unit) {
        block()
        current.value = all(includingRemoved = false).filter { !hidden.contains(it.name.lowercase()) }.toISet()
    }
}