package presenters.collections.internal

import kollections.List
import presenters.collections.ActionsManager
import presenters.collections.ColumnsManager
import presenters.collections.DataCollection
import presenters.collections.PaginationManager
import presenters.collections.Row
import presenters.collections.ScrollableList
import presenters.collections.SelectionManager
import presenters.collections.Table

@PublishedApi
internal class DataCollectionImpl<T>(
    override val paginator: PaginationManager<T>,
    override val selector: SelectionManager<T>,
    override val actions: ActionsManager<T>,
    override val columns: ColumnsManager<T>
) : Table<T>, ScrollableList<T>, DataCollection<T>,
    PaginationManager<T> by paginator,
    SelectionManager<T> by selector,
    ActionsManager<T> by actions {

    override val rows: List<Row<T>> get() = paginator.continuous

    override fun manageColumns(block: (ColumnsManager<T>) -> Unit): DataCollectionImpl<T> {
        columns.apply(block)
        return this
    }

    override fun manageActions(block: (ActionsManager<T>) -> Unit): DataCollectionImpl<T> {
        actions.apply(block)
        return this
    }
}