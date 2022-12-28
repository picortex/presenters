package presenters.collections.internal

import functions.Function1IO
import kollections.toIList
import functions.Function2IO
import presenters.collections.Page

abstract class AbstractPage<out T> : Page<T> {
    override val isEmpty: Boolean by lazy { items.size == 0 }
    override fun <R> map(transformer: (item: T) -> R) = Page(
        items = items.map { transformer(it.item) }.toIList(), capacity, number
    )

    override fun <R> mapIndexed(transformer: (index: Int, item: T) -> R) = Page(
        items = items.mapIndexed { index, it -> transformer(index, it.item) }.toIList(), capacity, number
    )

    override fun <R> map(transformer: Function1IO<T, R>): Page<R> = map(transformer::invoke)
    override fun <R> mapIndexed(transformer: Function2IO<Int, T, R>): Page<R> = mapIndexed(transformer::invoke)
}