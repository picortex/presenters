package presenters.collections

import kollections.iListOf
import kotlin.collections.Collection
import kollections.toIList
import presenters.collections.internal.PageImpl

inline fun <T> Page(
    items: Collection<T> = iListOf(),
    capacity: Int = items.size,
    number: Int = 1
): Page<T> = PageImpl(
    items = items.mapIndexed { index, it ->
        Row(index, it)
    }.toIList(),
    capacity = capacity,
    number = number
)