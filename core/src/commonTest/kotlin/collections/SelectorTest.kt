package collections

import expect.expect
import expect.toBe
import presenters.collections.Selected
import presenters.collections.CollectionPaginator
import presenters.collections.SelectionManager
import presenters.collections.internal.SelectionManagerImpl
import viewmodel.ViewModelConfig
import kotlin.test.Test

class SelectorTest {

    @Test
    fun should_select_a_row_by_number() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator)

        paginator.loadFirstPage()

        selector.select(row = 1)
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true)
        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(false)

        selector.select(row = 2)
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(false, "Row 1 was selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(true, "Row 2 was not selected")
    }

    @Test
    fun should_select_multiple_rows_by_number() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManagerImpl(paginator)

        paginator.loadFirstPage()

        selector.addSelection(1)
        selector.addSelection(2)
        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(true, "Row 2 was not selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Row 1 was not selected")
    }

    @Test
    fun should_select_multiple_rows_by_number_from_different_pages() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManagerImpl(paginator)

        paginator.loadFirstPage()

        selector.addSelection(1)
        selector.addSelection(2)

        paginator.loadNextPage()
        selector.addSelection(1)
        selector.addSelection(2)
        expect(selector.isRowSelectedOnPage(row = 2, page = 1)).toBe(true, "Row 2 was not selected")
        expect(selector.isRowSelectedOnPage(row = 1, page = 1)).toBe(true, "Row 1 was not selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(true, "Row 2 was not selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Row 1 was not selected")
    }

    @Test
    fun should_be_able_to_clear_selection_of_an_item() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManagerImpl(paginator)

        paginator.loadFirstPage()
        expect(paginator.currentPageOrNull?.number).toBe(1)

        selector.select(1)
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Row 1 was not selected")

        selector.unSelectRowInCurrentPage(1)
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(false, "Row 1 / Page 2: was selected")
    }

    @Test
    fun should_be_able_to_clear_selection_of_the_current_page_only() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManagerImpl(paginator)

        paginator.loadFirstPage()
        expect(paginator.currentPageOrNull?.number).toBe(1)

        selector.addSelection(1)
        selector.addSelection(2)

        paginator.loadNextPage()
        expect(paginator.currentPageOrNull?.number).toBe(2)
        selector.addSelection(1)
        selector.addSelection(2)

        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(true, "Row 2 / Page 2: was not selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Row 1 / Page 2: was not selected")

        selector.unSelectAllItemsInTheCurrentPage()

        expect(selector.isRowSelectedOnPage(row = 2, page = 1)).toBe(true, "Row 2 / Page 1: Was not selected")
        expect(selector.isRowSelectedOnPage(row = 1, page = 1)).toBe(true, "Row 1 / Page 1: Was not selected")

        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(false, "Row 2 / Page 2: was selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(false, "Row 1 / Page 2: was selected")
    }

    @Test
    fun should_be_able_to_clear_selection_from_all_pages() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManagerImpl(paginator)

        paginator.loadFirstPage()
        expect(paginator.currentPageOrNull?.number).toBe(1)

        selector.addSelection(1)
        selector.addSelection(2)

        paginator.loadNextPage()
        expect(paginator.currentPageOrNull?.number).toBe(2)
        selector.addSelection(1)
        selector.addSelection(2)

        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(true, "Row 2 / Page 2: was not selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Row 1 / Page 2: was not selected")

        selector.unSelectAllItemsInAllPages()

        expect(selector.isRowSelectedOnPage(row = 2, page = 1)).toBe(false, "Row 2 / Page 1: Was still selected after deselection")
        expect(selector.isRowSelectedOnPage(row = 1, page = 1)).toBe(false, "Row 1 / Page 1: Was still selected after deselection")

        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(false, "Row 2 / Page 2: was selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(false, "Row 1 / Page 2: was selected")
    }

    @Test
    fun should_be_able_to_select_all_items_in_the_current_page() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManagerImpl(paginator)

        paginator.loadFirstPage()
        expect(paginator.currentPageOrNull?.number).toBe(1)

        selector.addSelection(1)
        selector.addSelection(2)

        paginator.loadNextPage()
        expect(paginator.currentPageOrNull?.number).toBe(2)

        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(false, "Row 2 / Page 2: was already selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(false, "Row 1 / Page 2: was already selected")

        selector.selectAllItemsInTheCurrentPage()

        expect(selector.isRowSelectedOnPage(row = 2, page = 1)).toBe(false, "Row 2 / Page 1: Was still selected after deselection")
        expect(selector.isRowSelectedOnPage(row = 1, page = 1)).toBe(false, "Row 1 / Page 1: Was still selected after deselection")

        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(true, "Row 2 / Page 2: was not selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Row 1 / Page 2: was not selected")
    }

    @Test
    fun should_be_able_to_select_all_items_from_all_pages() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManagerImpl(paginator)

        paginator.loadFirstPage()
        expect(paginator.currentPageOrNull?.number).toBe(1)

        selector.addSelection(1)
        selector.addSelection(2)

        paginator.loadNextPage()
        expect(paginator.currentPageOrNull?.number).toBe(2)

        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(false, "Row 2 / Page 2: was already selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(false, "Row 1 / Page 2: was already selected")

        selector.selectAllItemsInAllPages()

        expect(selector.isRowSelectedOnPage(row = 2, page = 1)).toBe(true, "Row 2 / Page 1: Was still selected after deselection")
        expect(selector.isRowSelectedOnPage(row = 1, page = 1)).toBe(true, "Row 1 / Page 1: Was still selected after deselection")

        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(true, "Row 2 / Page 2: was not selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Row 1 / Page 2: was not selected")
    }

    @Test
    fun should_be_able_to_toggle_selection_of_current_page() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManagerImpl(paginator)

        paginator.loadFirstPage()
        expect(paginator.currentPageOrNull?.number).toBe(1)

        selector.toggleSelectionOfRowInCurrentPage(row = 1)
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Row 1 / Page 2: was supposed to be selected")

        selector.toggleSelectionOfRowInCurrentPage(row = 1)
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(false, "Row 1 / Page 2: was supposed to not be selected")
    }

    @Test
    fun should_be_able_to_get_the_selected_item() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManagerImpl(paginator)

        paginator.loadFirstPage()
        expect(paginator.currentPageOrNull?.number).toBe(1)

        selector.toggleSelectionOfRowInCurrentPage(row = 1)
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Row 1 / Page 2: was supposed to be selected")

        expect(selector.selected).toBe<Selected.Item<Person>>()
    }

    @Test
    fun should_be_able_to_selecte_by_object_instance() {
        val paginator = CollectionPaginator(Person.List, capacity = 5)
        val selector = SelectionManagerImpl(paginator)

        paginator.loadFirstPage()
        expect(paginator.currentPageOrNull?.number).toBe(1)

        paginator.loadNextPage()
        expect(paginator.currentPageOrNull?.number).toBe(2)

        selector.select(Person.List[3])
        expect(selector.isRowSelectedOnPage(row = 4, page = 1)).toBe(true, "Row 4 / Page 1: was supposed to be selected")

        expect(selector.selected).toBe<Selected.Item<Person>>()
    }
}