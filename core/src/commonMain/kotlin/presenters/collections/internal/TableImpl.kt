package presenters.collections.internal

import kotlinx.collections.interoperable.List
import live.MutableLive
import presenters.collections.*
import viewmodel.ViewModelConfig
import kotlin.js.JsExport

internal open class TableImpl<T>(
    override val paginator: PaginationManager<T>,
    override val selector: SelectionManager<T>,
    override val actionsManager: ActionsManager<T>,
    override val columns: List<Column<T>>,
    config: ViewModelConfig
) : PageableImpl<T>(config), Table<T>,
    PaginationManager<T> by paginator,
    SelectionManager<T> by selector,
    ActionsManager<T> by actionsManager