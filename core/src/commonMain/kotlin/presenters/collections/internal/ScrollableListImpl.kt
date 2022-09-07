package presenters.collections.internal

import kotlinx.collections.interoperable.List
import presenters.collections.*
import viewmodel.ViewModelConfig

class ScrollableListImpl<T>(
    override val paginator: PaginationManager<T>,
    override val selector: SelectionManager<T>,
    override val actionsManager: ActionsManager<T>,
    config: ViewModelConfig
) : PageableImpl<T>(config), ScrollableList<T>,
    PaginationManager<T> by paginator,
    SelectionManager<T> by selector,
    ActionsManager<T> by actionsManager {
    override val rows: List<Row<T>> get() = paginator.continuous
}