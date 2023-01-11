package presenters.collections

import kollections.toIList
import presenters.collections.internal.DataCollectionImpl
import kotlin.jvm.JvmSynthetic

@JvmSynthetic
fun <T> tableOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actionsManager: ActionsManager<T>,
    columns: List<Column<T>>
): Table<T> = DataCollectionImpl(paginator, selector, actionsManager, columns.toIList())

@JvmSynthetic
fun <T> tableOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actionsManager: ActionsManager<T>,
    builder: ColumnsBuilder<T>.() -> Unit
): Table<T> = DataCollectionImpl(paginator, selector, actionsManager, columnsOf(builder))

@JvmSynthetic
fun <T> simpleTableOf(
    items: Collection<T>,
    builder: ColumnsBuilder<T>.() -> Unit
): Table<T> {
    val paginator = SinglePagePaginator(items)
    paginator.loadFirstPage()
    val selector = SelectionManager(paginator)
    val actions = actionsOf(selector) {}
    val cols = columnsOf(builder)
    return DataCollectionImpl(paginator, selector, actions, cols)
}