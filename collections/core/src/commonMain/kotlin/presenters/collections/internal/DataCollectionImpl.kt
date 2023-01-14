package presenters.collections.internal

import kollections.List
import presenters.collections.ActionsManager
import presenters.collections.Column
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
    override val actionsManager: ActionsManager<T>,
    override val columns: List<Column<T>>
) : Table<T>, ScrollableList<T>, DataCollection<T>,
    PaginationManager<T> by paginator,
    SelectionManager<T> by selector,
    ActionsManager<T> by actionsManager {
    override val rows: List<Row<T>> get() = paginator.continuous
}