package presenters.collections.internal

import presenters.collections.Page
import presenters.collections.PageableRam

abstract class AbstractPageableRam<T> : PageableRam<T> {
    override fun readOrNull(page: Int, capacity: Int): Page<T>? = try {
        read(page, capacity)
    } catch (_: Throwable) {
        null
    }
}