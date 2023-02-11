package presenters.collections.internal

import kollections.toISet
import presenters.collections.Column
import presenters.collections.ColumnsBuilder
import presenters.collections.ColumnsManager
import presenters.collections.Row

@PublishedApi
internal class ColumnsManagerImpl<D>(
    private val builder: ColumnsBuilder<D>
) : ColumnsManager<D> {
    constructor(columns: MutableSet<Column<D>>) : this(ColumnsBuilder(columns.associateBy { it.key }.toMutableMap()))

    override val current = builder.current

    override fun all(includingRemoved: Boolean) = builder.columns.values.toISet()

    override fun remove(name: String): ColumnsManager<D> {
        builder.remove(name)
        return this
    }

    override fun add(name: String, accessor: (Row<D>) -> String): ColumnsManager<D> {
        builder.column(name, name, "N/A", accessor)
        return this
    }

    override fun hide(name: String): ColumnsManager<D> {
        builder.hide(name)
        return this
    }

    override fun show(name: String): ColumnsManager<D> {
        builder.show(name)
        return this
    }

    override fun index(name: String, idx: Int): ColumnsManager<D> {
        builder.index(name,idx)
        return this
    }

    override fun rename(prev: String, curr: String): ColumnsManager<D> {
        builder.rename(prev, curr)
        return this
    }
}