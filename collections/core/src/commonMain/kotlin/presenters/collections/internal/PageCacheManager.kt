package presenters.collections.internal

import presenters.collections.Page
import presenters.collections.SelectedItem

internal class PageCacheManager<T>(
    private val records: MutableMap<Int, PageCacheRecord<T>> = mutableMapOf()
) {
    fun save(capacity: Int, page: Page<T>): Page<T> {
        val record = records.getOrPut(capacity) { PageCacheRecord(capacity, mutableMapOf()) }
        record.pages[page.number] = page
        return page
    }

    fun load(capacity: Int, page: Int): Page<T>? {
        val record = records[capacity] ?: return null
        return record.pages[page]
    }

    fun load(capacity: Int, page: Int, row: Int): SelectedItem<T>? {
        val p = load(capacity, page) ?: return null
        val r = p.items.firstOrNull { it.number == row } ?: return null
        return SelectedItem(p, r)
    }

    fun clear() = records.clear()
}