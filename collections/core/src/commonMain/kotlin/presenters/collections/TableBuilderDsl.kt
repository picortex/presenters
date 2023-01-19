package presenters.collections

import kollections.iEmptyList
import kollections.toIList
import presenters.collections.internal.ColumnsManagerImpl
import presenters.collections.internal.DataCollectionImpl
import kotlin.jvm.JvmSynthetic

@JvmSynthetic
fun <T> tableOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actionsManager: ActionsManager<T>
): Table<T> = DataCollectionImpl(
    paginator, selector, actionsManager,
    ColumnsManagerImpl(paginator, selector, actionsManager, mutableListOf())
)

@JvmSynthetic
fun <T> tableOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actionsManager: ActionsManager<T>,
    columns: Collection<Column<T>>
): Table<T> = DataCollectionImpl(
    paginator, selector, actionsManager,
    ColumnsManagerImpl(paginator, selector, actionsManager, columns.toMutableList())
)

@JvmSynthetic
fun <T> tableOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actionsManager: ActionsManager<T>,
    builder: ColumnsBuilder<T>.() -> Unit
): Table<T> = DataCollectionImpl(
    paginator, selector, actionsManager,
    ColumnsManagerImpl(paginator, selector, actionsManager, columnsOf(builder).toMutableList())
)

@JvmSynthetic
fun <T> simpleTableOf(
    items: Collection<T>,
    builder: ColumnsBuilder<T>.() -> Unit
): Table<T> {
    val paginator = SinglePagePaginator(items)
    paginator.loadFirstPage()
    val selector = SelectionManager(paginator)
    return DataCollectionImpl(
        paginator, selector, actionsOf(),
        ColumnsManagerImpl(paginator, selector, actionsOf(), columnsOf(builder).toMutableList())
    )
}