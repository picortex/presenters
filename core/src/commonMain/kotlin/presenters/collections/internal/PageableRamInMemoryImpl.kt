package presenters.collections.internal

import presenters.collections.Page

class PageableRamInMemory<T>(
    private val map: MutableMap<String, Page<T>> = mutableMapOf()
) : AbstractPageableRam<T>() {
    inline fun tokenize(page: Page<T>) = tokenize(page.number, page.capacity)
    inline fun tokenize(page: Int, capacity: Int) = "{page:$page,capacity:$capacity}"

    override fun read(page: Int, capacity: Int): Page<T> {
        return map[tokenize(page, capacity)] ?: throw RuntimeException(
            "Page $page of Capacity $capacity was not found in memory"
        )
    }

    override fun write(page: Page<T>): Page<T> {
        map[tokenize(page)] = page
        return page
    }
}