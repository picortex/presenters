package presenters.collections.internal

import kollections.List
import presenters.collections.ActionsManager
import presenters.collections.Column
import presenters.collections.PaginationManager
import presenters.collections.SelectionManager
import presenters.collections.Table

internal open class TableImpl<T>(
    override val paginator: PaginationManager<T>,
    override val selector: SelectionManager<T>,
    override val actionsManager: ActionsManager<T>,
    override val columns: List<Column<T>>
) : PageableImpl<T>(), Table<T>,
    PaginationManager<T> by paginator,
    SelectionManager<T> by selector,
    ActionsManager<T> by actionsManager