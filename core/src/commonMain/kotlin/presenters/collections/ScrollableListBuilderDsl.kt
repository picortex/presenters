package presenters.collections

import presenters.collections.internal.ScrollableListImpl
import viewmodel.ViewModelConfig
import kotlin.jvm.JvmSynthetic

@JvmSynthetic
fun <T> scrollableListOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actionsManager: ActionsManager<T>,
    config: ViewModelConfig
): ScrollableList<T> = ScrollableListImpl(paginator, selector, actionsManager, config)