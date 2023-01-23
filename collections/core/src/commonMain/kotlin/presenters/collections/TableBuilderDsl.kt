@file:Suppress("NOTHING_TO_INLINE")

package presenters.collections

import presenters.collections.internal.DataCollectionImpl
import kotlin.jvm.JvmSynthetic

@JvmSynthetic
inline fun <T> tableOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actions: ActionsManager<T>,
    columns: ColumnsManager<T>
): Table<T> = DataCollectionImpl(paginator, selector, actions, columns)

/*
 * DEAR DEVELOPER,
 * Do not mark this class as inline, because it tends to increase bundle size
 * due to very long columns declarations that can be found in complex tables
 */
@JvmSynthetic
fun <T> tableOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actions: ActionsManager<T>,
    columns: ColumnsBuilder<T>.() -> Unit
): Table<T> = DataCollectionImpl(paginator, selector, actions, columnsOf(emptyList(), columns))

@JvmSynthetic
inline fun <T> tableOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actions: ActionsManager<T>
): Table<T> = DataCollectionImpl(paginator, selector, actions, columnsOf())

@JvmSynthetic
inline fun <T> tableOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>
): Table<T> = DataCollectionImpl(paginator, selector, actionsOf(selector) {}, columnsOf())

/*
 * DEAR DEVELOPER,
 * Do not mark this class as inline, because it tends to increase bundle size
 * due to very long columns declarations that can be found in complex tables
 */
@JvmSynthetic
inline fun <T> simpleTableOf(
    items: Collection<T>,
    noinline columns: ColumnsBuilder<T>.() -> Unit
): Table<T> {
    val paginator = SinglePagePaginator(items)
    paginator.loadFirstPage()
    val selector = SelectionManager(paginator)
    return DataCollectionImpl(paginator, selector, actionsOf(), columnsOf(emptyList(), columns))
}