package presenters.collections.internal

import kotlinx.collections.interoperable.List
import presenters.collections.Row

data class PageImpl<out T>(
    override val items: List<Row<T>>,
    override val capacity: Int,
    override val number: Int
) : AbstractPage<T>()