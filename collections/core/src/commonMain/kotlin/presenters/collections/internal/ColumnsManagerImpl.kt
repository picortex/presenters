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
    private val columns: List<Column<D>>
) : ColumnsManager<D> {
    override val size = columns.size

    override fun get() = columns

    override fun remove(name: String) = ColumnsManagerImpl(
        paginator,selector,actionsManager,
        columns.filter { it.name != name }.toIList()
    )

    override fun add(name: String, accessor: (Row<D>) -> String) = ColumnsManagerImpl(
        paginator,selector,actionsManager,
        (columns + Column.Data(name, accessor)).toIList()
    )

    override fun finish(columns: List<Column<D>>) = DataCollectionImpl(
        paginator,selector,actionsManager,
        ColumnsManagerImpl(paginator, selector, actionsManager, columns)
    )
}