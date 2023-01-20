package presenters.collections.internal

import kollections.toIList
import presenters.collections.Column
import presenters.collections.ColumnsBuilder
import presenters.collections.ColumnsManager
import presenters.collections.Row

@PublishedApi
internal class ColumnsManagerImpl<D>(
    private val builder: ColumnsBuilder<D>
) : ColumnsManager<D> {

    constructor(columns: MutableList<Column<D>>) : this(ColumnsBuilder(columns))

    override val size get() = builder.columns.size

    override fun get() = builder.columns.toIList()

    override fun remove(name: String): ColumnsManager<D> {
        val col = builder.columns.find { it.name.equals(name, ignoreCase = true) } ?: return this
        builder.columns.remove(col)
        return this
    }

    override fun add(name: String, accessor: (Row<D>) -> String): ColumnsManager<D> {
        builder.columns.add(Column.Data(name, accessor))
        return this
    }
}