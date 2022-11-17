package presenters.collections

import kollections.toIList
import presenters.collections.internal.TableImpl
import viewmodel.ViewModelConfig
import kotlin.jvm.JvmSynthetic

@JvmSynthetic
fun <T> tableOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actionsManager: ActionsManager<T>,
    columns: List<Column<T>>
): Table<T> = TableImpl(paginator, selector, actionsManager, columns.toIList())

@JvmSynthetic
fun <T> simpleTableOf(
    items: Collection<T>,
    config: ViewModelConfig = ViewModelConfig(),
    builder: ColumnsBuilder<T>.() -> Unit
): Table<T> {
    val paginator = SinglePagePaginator(items)
    paginator.loadFirstPage()
    val selector = SelectionManager(paginator)
    val actions = actionsOf(selector) {}
    val cols = columnsOf(builder)
    return TableImpl(paginator, selector, actions, cols)
}