package presenters.collections.internal

import presenters.collections.Page

internal data class PageCacheRecord<T>(
    val capacity: Int,
    val pages: MutableMap<Int, Page<T>>
)