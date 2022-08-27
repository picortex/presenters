@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections.internal

import presenters.collections.ActionsManager
import presenters.collections.PaginationManager
import presenters.collections.ScrollableList
import presenters.collections.SelectionManager
import viewmodel.ViewModelConfig
import kotlin.js.JsExport

class ScrollableListImpl<T>(
    override val paginator: PaginationManager<T>,
    override val selector: SelectionManager<T>,
    override val actionsManager: ActionsManager<T>,
    config: ViewModelConfig
) : PageableImpl<T>(config), ScrollableList<T>,
    PaginationManager<T> by paginator,
    SelectionManager<T> by selector,
    ActionsManager<T> by actionsManager