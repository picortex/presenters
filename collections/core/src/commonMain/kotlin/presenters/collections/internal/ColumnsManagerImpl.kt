package presenters.collections.internal

import kollections.List
import kollections.toIList
import presenters.collections.ActionsManager
import presenters.collections.Column
import presenters.collections.ColumnsManager
import presenters.collections.PaginationManager
import presenters.collections.Row
import presenters.collections.SelectionManager

@PublishedApi
internal class ColumnsManagerImpl<D>(
    private val paginator: PaginationManager<D>,
    private val selector: SelectionManager<D>,
    private val actionsManager: ActionsManager<D>,
    private val columns: MutableList<Column<D>>
) : ColumnsManager<D> {
    override val size get() = columns.size

    override fun get() = columns.toIList()

    override fun remove(name: String): ColumnsManager<D> {
        val col = columns.find { it.name.equals(name, ignoreCase = true) } ?: return this
        columns.remove(col)
        return this
    }

    override fun add(name: String, accessor: (Row<D>) -> String): ColumnsManager<D> {
        columns.add(Column.Data(name, accessor))
        return this
    }

    override fun finish(columns: List<Column<D>>) = DataCollectionImpl(
        paginator, selector, actionsManager,
        ColumnsManagerImpl(paginator, selector, actionsManager, columns.toMutableList())
    )
}