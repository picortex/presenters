package presenters.collections.internal

import presenters.collections.*
import viewmodel.ViewModel
import viewmodel.ViewModelConfig

abstract class AbstractSelectionManager<T>(
    private val paginator: PaginationManager<T>
) : SelectionManager<T> {

    protected val currentLoadedPage get() = paginator.currentPageOrNull

    override fun selectAllItemsInTheCurrentPage() = selectAllRowsInPage(currentLoadedPage?.number)

    override fun selectAllItemsInPage(page: Int) = selectAllRowsInPage(page)

    abstract fun selectAllRowsInPage(page: Int?)

    override fun select(row: Int) = selectRow(row, currentLoadedPage?.number)

    override fun select(row: Int, page: Int) = selectRow(row, page)

    abstract fun selectRow(row: Int, page: Int?)

    override fun select(obj: T) {
        val pos = paginator.find(obj) ?: return
        selectRow(pos.row.number, pos.page.number)
    }

    override fun addSelection(row: Int) = addRowSelection(row, currentLoadedPage?.number)

    override fun addSelection(row: Int, page: Int) = addRowSelection(row, page)

    override fun addSelection(obj: T) {
        val pos = paginator.find(obj) ?: return
        addRowSelection(pos.row.number, pos.page.number)
    }

    abstract fun addRowSelection(row: Int, page: Int?)

    override fun unSelectRowInCurrentPage(row: Int) = unSelectRowFromPage(row, currentLoadedPage?.number)

    override fun unSelectRowInPage(row: Int, page: Int) = unSelectRowFromPage(row, page)

    override fun unSelectAllItemsInTheCurrentPage() = unSelectAllRowsInPage(currentLoadedPage?.number)

    override fun unSelectAllItemsInPage(page: Int) = unSelectAllRowsInPage(page)

    abstract fun unSelectAllRowsInPage(page: Int?)

    abstract fun unSelectRowFromPage(row: Int, page: Int?)

    override fun unSelect(item: T) {
        val pos = paginator.find(item) ?: return
        unSelectRowInPage(pos.row.number, pos.page.number)
    }

    override fun isRowSelectedOnCurrentPage(row: Int) = isRowItemSelected(row, currentLoadedPage?.number)

    override fun isRowSelectedOnPage(row: Int, page: Int) = isRowItemSelected(row, page)

    override fun isPageSelectedWholly(page: Int): Boolean = isPageSelectedWithNoExceptions(page)

    override fun isCurrentPageSelectedWholly(): Boolean = isPageSelectedWithNoExceptions(currentLoadedPage?.number)

    abstract fun isPageSelectedWithNoExceptions(page: Int?): Boolean

    override fun isCurrentPageSelectedPartially(): Boolean = isPageSelectedButPartially(currentLoadedPage?.number)

    override fun isPageSelectedPartially(page: Int): Boolean = isPageSelectedButPartially(page)

    abstract fun isPageSelectedButPartially(page: Int?): Boolean

    abstract fun isRowItemSelected(row: Int, page: Int?): Boolean

    override fun toggleSelectionOfRowInCurrentPage(row: Int) = toggleRowSelection(row, currentLoadedPage?.number)

    override fun toggleSelectionOfRowInPage(row: Int, page: Int) = toggleRowSelection(row, page)

    private fun toggleRowSelection(row: Int, page: Int?) =
        if (isRowItemSelected(row, page)) unSelectRowFromPage(row, page) else select(row)

    override fun toggleSelectionOfCurrentPage() = toggleSelectionOfANullablePage(currentLoadedPage?.number)

    override fun toggleSelectionOfPage(page: Int) = toggleSelectionOfANullablePage(page)

    private fun toggleSelectionOfANullablePage(page: Int?) = if (isPageSelectedWithNoExceptions(page)) {
        unSelectAllRowsInPage(page)
    } else {
        selectAllRowsInPage(page)
    }
}