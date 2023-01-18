package presenters.collections.internal

import presenters.collections.Page
import presenters.collections.Row
import presenters.collections.SelectedItem

internal class PageCacheManager<T>(
    internal val records: MutableMap<Int, PageCacheRecord<T>> = mutableMapOf()
) {
    fun save(page: Page<T>): Page<T> {
        val record = records.getOrPut(page.capacity) { PageCacheRecord(page.capacity, mutableMapOf()) }
        record.pages[page.number] = page
        return page
    }

    fun load(page: Int, capacity: Int): Page<T>? {
        val record = records[capacity] ?: return null
        return record.pages[page]
    }

    fun load(row: Int, page: Int, capacity: Int): SelectedItem<T>? {
        val p = load(page, capacity) ?: return null
        val r = p.items.firstOrNull { it.number == row } ?: return null
        return SelectedItem(p, r)
    }

    fun clear() = records.clear()

    fun load(item: T, capacity: Int): SelectedItem<T>? {
        val record = records[capacity] ?: return null
        var page: Page<T>? = null
        var row: Row<T>? = null
        val pages = record.pages.values
        for (p in pages) {
            if (row != null) break
            page = p
            row = p.items.find { it.item == item }
        }
        if (page == null) return null
        if (row == null) return null
        return SelectedItem(page, row)
    }
}