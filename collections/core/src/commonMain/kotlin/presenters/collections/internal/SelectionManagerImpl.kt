package presenters.collections.internal

import kollections.List
import kollections.iListOf
import kollections.toIList
import live.MutableLive
import live.mutableLiveOf
import presenters.collections.PaginationManager
import presenters.collections.Selected
import presenters.collections.SelectedGlobal
import presenters.collections.SelectedItem
import presenters.collections.SelectedItems
import presenters.collections.SelectedNone
import presenters.collections.SelectedPage
import presenters.collections.SelectorState

class SelectionManagerImpl<T>(
    private val paginator: PaginationManager<T>
) : AbstractSelectionManager<T>(paginator) {

    override val selected: MutableLive<Selected<T>> = mutableLiveOf(SelectedNone)

    private fun SelectorState.mapToSelected(): Selected<T> {
        TODO()
//        when (val s = this) {
//            is SelectorState.AllSelected -> Selected.Global(
//                exceptions = s.exceptions.loadedFromPaginatorMemory()
//            )
//
//            is SelectorState.Item -> try {
//                val items = paginator.readPageFromMemoryOrEmpty(s.page, paginator.capacity).items
//                Selected.Item(items.first { row -> row.number == s.number }.item)
//            } catch (err: Throwable) {
//                Selected.None
//            }
//
//            is SelectorState.Items -> Selected.Items(
//                values = s.items.loadedFromPaginatorMemory()
//            )
//
//            is SelectorState.NoSelected -> Selected.None
//        }
    }

    private fun List<SelectorState.Item>.loadedFromPaginatorMemory(): List<T> = mapNotNull { item ->
        paginator.readPageFromMemoryOrEmpty(item.page, paginator.capacity).items.firstOrNull { row ->
            row.number == item.number
        }?.item
    }.toIList()

    override fun selectAllRowsInPage(page: Int?) {
        val p = page ?: return
        val rows = paginator.readPageFromMemoryOrEmpty(p, paginator.capacity)
        TODO()
//        state.value = SelectorState.Items(
//            items = rows.items.map { SelectorState.Item(it.number, p) }.toIList()
//        )
    }

    override fun selectAllItemsInAllPages() {
        TODO()
//        state.value = SelectorState.AllSelected(exceptions = iListOf())
    }

    override fun unSelectAllItemsInAllPages() {
        TODO()
//        state.value = SelectorState.NoSelected
    }

    override fun unSelectAllRowsInPage(page: Int?) {
        TODO()
//        state.value = when (val s = state.value) {
//            is SelectorState.NoSelected -> SelectorState.NoSelected
//            is SelectorState.Item -> if (s.page == page) SelectorState.NoSelected else s
//            is SelectorState.Items -> {
//                val items = s.items.filter { it.page != page }.toIList()
//                when {
//                    items.size > 1 -> SelectorState.Items(items)
//                    items.size == 1 -> items.first()
//                    else -> SelectorState.NoSelected
//                }
//            }
//
//            is SelectorState.AllSelected -> SelectorState.NoSelected
//        }
    }

    override fun isPageSelectedButPartially(page: Int?): Boolean {
        TODO()
//        when (val s = state.value) {
//            is SelectorState.NoSelected -> false
//            is SelectorState.Item -> s.page == page
//            is SelectorState.Items -> s.items.any { it.page == page }
//            is SelectorState.AllSelected -> true
//        }
    }

    override fun isPageSelectedWithNoExceptions(page: Int?): Boolean {
        val p = page ?: return false
        TODO()
//        return when (val s = state.value) {
//            is SelectorState.NoSelected -> false
//            is SelectorState.Item -> false
//            is SelectorState.Items -> {
//                paginator.readPageFromMemoryOrEmpty(p, paginator.capacity).items.map { row ->
//                    s.items.find { it.page == p && it.number == row.number } != null
//                }.all { it }
//            }
//
//            is SelectorState.AllSelected -> true
//        }
    }

    override fun unSelectRowFromPage(row: Int, page: Int?) {
        TODO()
//        state.value = when (val s = state.value) {
//            is SelectorState.NoSelected -> SelectorState.NoSelected
//            is SelectorState.Item -> if (s.page == page && s.number == row) SelectorState.NoSelected else s
//            is SelectorState.Items -> {
//                val items = s.items.filter { it.page != page && it.number != row }.toIList()
//                when {
//                    items.size > 1 -> SelectorState.Items(items)
//                    items.size == 1 -> items.first()
//                    else -> SelectorState.NoSelected
//                }
//            }
//
//            is SelectorState.AllSelected -> if (page != null) s.copy(
//                exceptions = (s.exceptions.toMutableList() + SelectorState.Item(row, page)).toIList()
//            ) else s
//        }
    }

    override fun addRowSelection(row: Int, page: Int?) {
        val pageNo = page ?: return
        TODO()
//        state.value = when (val s = state.value) {
//            is SelectorState.NoSelected -> SelectorState.Item(row, pageNo)
//            is SelectorState.Item -> SelectorState.Items(
//                items = iListOf(s, SelectorState.Item(row, pageNo))
//            )
//
//            is SelectorState.Items -> SelectorState.Items(
//                items = (s.items.toMutableList() + SelectorState.Item(row, pageNo)).toIList()
//            )
//
//            is SelectorState.AllSelected -> SelectorState.Item(row, pageNo)
//        }
    }

    override fun selectRow(row: Int, page: Int?) {
        val p = page ?: return
        val item = paginator.findRow(row, page)
        TODO()
    }

    override fun isRowItemSelected(row: Int, page: Int?) = when (val s = selected.value) {
        is SelectedNone -> false
        is SelectedItem -> s.row.number == row && s.page.number == page
        is SelectedItems -> s.values.any { it.page.number == page && it.row.number == row }
        is SelectedPage -> s.page.number == page
        is SelectedGlobal -> !s.exceptions.map { it.number }.contains(row)
    }
}