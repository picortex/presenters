package presenters.collections.internal

import functions.Function1
import functions.Function2
import kollections.List
import kollections.toIList
import presenters.collections.Page
import presenters.collections.Row

@PublishedApi
internal data class PageImpl<out T>(
    override val items: List<Row<T>>,
    override val capacity: Int,
    override val number: Int
) : Page<T> {
    override val isEmpty = items.size == 0

    override val hasMore get() = !isLastPage

    override val isFistPage = number == 1

    override val isLastPage = items.size < capacity

    override fun <R> map(transformer: (item: T) -> R) = Page(
        items = items.map { transformer(it.item) }.toIList(), capacity, number
    )

    override fun <R> mapIndexed(transformer: (index: Int, item: T) -> R) = Page(
        items = items.mapIndexed { index, it -> transformer(index, it.item) }.toIList(), capacity, number
    )

    override fun <R> map(transformer: Function1<T, R>): Page<R> = map(transformer::invoke)
    override fun <R> mapIndexed(transformer: Function2<Int, T, R>): Page<R> = mapIndexed(transformer::invoke)

    override fun toString(): String = buildString {
        appendLine("Page(number = $number, capacity= $capacity")
        items.forEach {
            appendLine("  ${it.number}. ${it.item}")
        }
        appendLine(")")
    }
}