package presenters.collections.internal

import kotlinx.collections.interoperable.iListOf
import kotlinx.collections.interoperable.toInteroperableList
import live.MutableLive
import presenters.collections.PaginationManager
import presenters.collections.Selected
import presenters.collections.SelectorState
import viewmodel.ViewModelConfig

class SelectionManagerImpl<T>(
    private val paginator: PaginationManager<T>,
    config: ViewModelConfig,
) : AbstractSelectionManager<T>(paginator, config) {

    override val state: MutableLive<SelectorState> get() = ui

    override val selected: Selected<T>
        get() = when (val state = ui.value) {
            is SelectorState.AllSelected -> Selected.Global(
                exceptions = state.exceptions.loadedFromPaginatorMemory()
            )

            is SelectorState.Item -> Selected.Item(
                paginator.readPageFromMemory(state.page, paginator.capacity).items.first { row ->
                    row.number == state.number
                }.item
            )

            is SelectorState.Items -> Selected.Items(
                values = state.items.loadedFromPaginatorMemory()
            )

            is SelectorState.NoSelected -> Selected.None
        }

    private fun List<SelectorState.Item>.loadedFromPaginatorMemory() = map { item ->
        paginator.readPageFromMemory(item.page, paginator.capacity).items.first { row -> row.number == item.number }.item
    }.toInteroperableList()

    override fun selectAllRowsInPage(page: Int?) {
        val p = page ?: return
        val rows = paginator.readPageFromMemory(p, paginator.capacity)
        ui.value = SelectorState.Items(
            items = rows.items.map { SelectorState.Item(it.number, p) }.toInteroperableList()
        )
    }

    override fun selectAllItemsInAllPages() {
        ui.value = SelectorState.AllSelected()
    }

    override fun unSelectAllItemsInAllPages() {
        ui.value = SelectorState.NoSelected
    }

    override fun unSelectAllRowsInPage(page: Int?) {
        ui.value = when (val state = ui.value) {
            is SelectorState.NoSelected -> SelectorState.NoSelected
            is SelectorState.Item -> if (state.page == page) SelectorState.NoSelected else state
            is SelectorState.Items -> {
                val items = state.items.filter { it.page != page }.toInteroperableList()
                when {
                    items.size > 1 -> SelectorState.Items(items)
                    items.size == 1 -> items.first()
                    else -> SelectorState.NoSelected
                }
            }

            is SelectorState.AllSelected -> SelectorState.NoSelected
        }
    }

    override fun isPageSelectedButPartially(page: Int?): Boolean = when (val state = ui.value) {
        is SelectorState.NoSelected -> false
        is SelectorState.Item -> state.page == page
        is SelectorState.Items -> state.items.any { it.page == page }
        is SelectorState.AllSelected -> true
    }

    override fun isPageSelectedWithNoExceptions(page: Int?): Boolean {
        val p = page ?: return false
        return when (val state = ui.value) {
            is SelectorState.NoSelected -> false
            is SelectorState.Item -> false
            is SelectorState.Items -> {
                paginator.readPageFromMemory(p, paginator.capacity).items.map { row ->
                    state.items.find { it.page == p && it.number == row.number } != null
                }.all { it }
            }

            is SelectorState.AllSelected -> true
        }
    }

    override fun unSelectRowFromPage(row: Int, page: Int?) {
        ui.value = when (val state = ui.value) {
            is SelectorState.NoSelected -> SelectorState.NoSelected
            is SelectorState.Item -> if (state.page == page && state.number == row) SelectorState.NoSelected else state
            is SelectorState.Items -> {
                val items = state.items.filter { it.page != page && it.number != row }.toInteroperableList()
                when {
                    items.size > 1 -> SelectorState.Items(items)
                    items.size == 1 -> items.first()
                    else -> SelectorState.NoSelected
                }
            }

            is SelectorState.AllSelected -> if (page != null) state.copy(
                exceptions = (state.exceptions.toMutableList() + SelectorState.Item(row, page)).toInteroperableList()
            ) else state
        }
    }

    override fun addRowSelection(row: Int, page: Int?) {
        val pageNo = page ?: return
        ui.value = when (val state = ui.value) {
            is SelectorState.NoSelected -> SelectorState.Item(row, pageNo)
            is SelectorState.Item -> SelectorState.Items(
                items = iListOf(state, SelectorState.Item(row, pageNo))
            )

            is SelectorState.Items -> SelectorState.Items(
                items = (state.items.toMutableList() + SelectorState.Item(row, pageNo)).toInteroperableList()
            )

            is SelectorState.AllSelected -> SelectorState.Item(row, pageNo)
        }
    }

    override fun selectRow(row: Int, page: Int?) {
        val p = page ?: return
        ui.value = SelectorState.Item(row, p)
    }

    override fun isRowItemSelected(row: Int, page: Int?) = when (val state = ui.value) {
        is SelectorState.NoSelected -> false
        is SelectorState.Item -> state.number == row && state.page == page
        is SelectorState.Items -> state.items.any { it.number == row && it.page == page }
        is SelectorState.AllSelected -> true
    }
}