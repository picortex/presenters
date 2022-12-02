package collections

import expect.expect
import presenters.collections.CollectionPaginator
import presenters.collections.SelectionManager
import presenters.collections.actionsOf
import presenters.collections.columnsOf
import presenters.collections.renderToConsole
import presenters.collections.tableOf
import kotlin.test.Test

class TableTest {

    fun PersonTableColumns() = columnsOf<Person> {
        selectable()
        column("No") { it.number.toString() }
        column("name") { it.item.name }
        column("age") { it.item.age.toString() }
    }

    @Test
    fun can_be_assigned_a_paginator() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator)
        val action = actionsOf(selector) {}
        val table = tableOf(paginator, selector, action, PersonTableColumns())
        table.renderToConsole()
        expect(table.currentPageOrNull?.number).toBe(null)

        table.refresh()
        table.renderToConsole()

        table.loadNextPage()
        table.renderToConsole()

        table.loadNextPage()
        table.renderToConsole()
    }

    @Test
    fun should_be_able_to_select_table_items() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator)
        val action = actionsOf(selector) {}
        val table = tableOf(paginator, selector, action, PersonTableColumns())
        table.loadFirstPage()
        table.renderToConsole()

        table.select(row = 1)
        selector.select(row = 1)
        expect(table.isCurrentPageSelectedPartially()).toBe(true, "Table was supposed to be partially selected")
        table.renderToConsole()
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Explicit selector failed to select")
        expect(table.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Implicit selector failed to select")
    }

    @Test
    fun should_be_able_to_select_the_whole_current_page() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator)
        val action = actionsOf(selector) {}
        val table = tableOf(paginator, selector, action, PersonTableColumns())

        table.loadFirstPage()
        table.renderToConsole()

        table.selectAllItemsInTheCurrentPage()
        table.renderToConsole()
        expect(table.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Implicit selector failed to select")
    }

    @Test
    fun should_be_able_to_retrieve_primary_actions() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator)
        val actions = actionsOf(selector) {
            primary {
                on("Create Person") { println("Just create da nigga") }
            }

            single {
                on("View ${it.name}") { println("Now viewing ${it.name}") }
            }
        }
        val table = tableOf(paginator, selector, actions, PersonTableColumns())

        table.loadFirstPage()

        expect(table.actions).toBeOfSize(1)

        table.select(1)
        table.renderToConsole()

        expect(table.actions).toBeOfSize(2)
    }
}