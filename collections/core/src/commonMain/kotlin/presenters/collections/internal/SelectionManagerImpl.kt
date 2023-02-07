package presenters.collections.internal

import kollections.iMapOf
import kollections.iSetOf
import kollections.to
import kollections.toIList
import kollections.toIMap
import kollections.toISet
import live.MutableLive
import live.mutableLiveOf
import presenters.collections.Page
import presenters.collections.PaginationManager
import presenters.collections.Row
import presenters.collections.Selected
import presenters.collections.SelectedGlobal
import presenters.collections.SelectedItem
import presenters.collections.SelectedItems
import presenters.collections.SelectedNone
import presenters.collections.SelectionManager

class SelectionManagerImpl<T>(
    private val paginator: PaginationManager<T>
) : AbstractSelectionManager<T>(paginator) {

    override val selected: MutableLive<Selected<T>> = mutableLiveOf(SelectedNone)

    override fun selectAllRowsInPage(page: Int?) {
        val pageNo = page ?: return
        val p = paginator.find(pageNo) ?: return
        selected.value = SelectedItems(
            page = iMapOf(p to p.items.toISet())
        )
    }

    override fun selectAllItemsInAllPages() {
        selected.value = SelectedGlobal(iSetOf())
    }

    override fun unSelectAllItemsInAllPages() {
        selected.value = SelectedNone
    }

    private fun SelectedItems<T>.unSelectAllRowsInPage(page: Int?): Selected<T> {
        val map = this.page.mapValues { it.value.toMutableSet() }.toMutableMap()
        val p = map.keys.find { it.number == page } ?: return this
        map.remove(p)
        return readjustSelectedItems(map)
    }

    override fun unSelectAllRowsInPage(page: Int?) {
        selected.value = when (val s = selected.value) {
            is SelectedNone -> s
            is SelectedItem -> if (s.page.number == page) SelectedNone else s
            is SelectedItems -> s.unSelectAllRowsInPage(page)
            is SelectedGlobal -> SelectedNone
        }
    }

    private fun SelectedItems<T>.isPageSelectedButPartially(page: Int?): Boolean {
        val entry = this.page.find { it.key.number == page } ?: return false
        return entry.key.capacity != entry.value.size
    }

    override fun isPageSelectedButPartially(page: Int?): Boolean = when (val s = selected.value) {
        is SelectedNone -> false
        is SelectedItem -> s.page.number == page
        is SelectedItems -> s.isPageSelectedButPartially(page)
        is SelectedGlobal -> s.exceptions.any { it.page.number == page }
    }

    private fun SelectedItems<T>.isPageSelectedWithNoExceptions(page: Int?): Boolean {
        val entry = this.page.find { it.key.number == page } ?: return false
        return entry.key.capacity == entry.value.size
    }

    override fun isPageSelectedWithNoExceptions(page: Int?): Boolean = when (val s = selected.value) {
        is SelectedNone -> false
        is SelectedItem -> false
        is SelectedItems -> s.isPageSelectedWithNoExceptions(page)
        is SelectedGlobal -> !s.exceptions.any { it.page.number == page }
    }

    private fun SelectedItems<T>.unselectRowFromPage(row: Int, page: Int): Selected<T> {
        val map = this.page.mapValues { it.value.toMutableSet() }.toMutableMap()
        val p = map.keys.find { it.number == page } ?: return this
        val r = map[p]?.find { it.number == row } ?: return this
        map[p]?.remove(r)
        if (map[p].isNullOrEmpty()) map.remove(p)
        return readjustSelectedItems(map)
    }

    private fun readjustSelectedItems(map: Map<Page<T>, Set<Row<T>>>): Selected<T> = when {
        map.isEmpty() -> SelectedNone

        map.size == 1 && map.entries.first().value.size == 1 -> {
            val entry = map.entries.first()
            SelectedItem(entry.key, entry.value.first())
        }

        else -> SelectedItems(map.mapValues { it.value.toISet() }.toIMap())
    }

    override fun unSelectRowFromPage(row: Int, page: Int?) {
        val pageNo = page ?: return
        selected.value = when (val s = selected.value) {
            is SelectedNone -> s
            is SelectedItem -> if (s.page.number == page && s.row.number == row) SelectedNone else s
            is SelectedItems -> s.unselectRowFromPage(row, pageNo)
            is SelectedGlobal -> SelectedGlobal(s.exceptions.filter { it.page.number == page && it.row.number == row }.toISet())
        }
    }

    private fun SelectedItem<T>.addRowSelection(row: Int, page: Int): Selected<T> {
        val item = paginator.find(row, page) ?: return this
        return SelectedItems(iMapOf(item.page to iSetOf(this.row, item.row)))
    }

    private fun SelectedItems<T>.addRowSelection(row: Int, page: Int): Selected<T> {
        val item = paginator.find(row, page) ?: return this
        val map = this.page.mapValues { it.value.toMutableSet() }.toMutableMap()
        map.getOrPut(item.page) { mutableSetOf() }.add(item.row)
        return SelectedItems(map.mapValues { it.value.toISet() }.toIMap())
    }

    override fun addRowSelection(row: Int, page: Int?) {
        val pageNo = page ?: return
        selected.value = when (val s = selected.value) {
            is SelectedNone -> paginator.find(row, pageNo) ?: return
            is SelectedItem -> s.addRowSelection(row, pageNo)
            is SelectedItems -> s.addRowSelection(row, pageNo)
            is SelectedGlobal -> SelectedGlobal(s.exceptions.filter { it.page.number == page && it.row.number == row }.toISet())
        }
    }

    override fun selectRow(row: Int, page: Int?) {
        val p = page ?: return
        val item = paginator.find(row, page = p) ?: return
        selected.value = item
    }

    override fun isRowItemSelected(row: Int, page: Int?) = when (val s = selected.value) {
        is SelectedNone -> false
        is SelectedItem -> s.row.number == row && s.page.number == page
        is SelectedItems -> s.page.toIList().any { (p, rows) -> p.number == page && rows.map { it.number }.contains(row) }
        is SelectedGlobal -> !s.exceptions.any { it.page.number == page && it.row.number == row }
    }

    override fun <R> map(transform: (T) -> R): SelectionManager<R> = SelectionManagerImpl(paginator.map(transform))
}