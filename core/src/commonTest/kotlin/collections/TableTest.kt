package collections

import expect.expect
import koncurrent.SynchronousExecutor
import presenters.collections.*
import presenters.collections.tabulateToConsole
import viewmodel.ViewModelConfig
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
        val config = ViewModelConfig(executor = SynchronousExecutor)
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator, config)
        val action = actionsOf(selector) {}
        val table = tableOf(paginator, selector, action, PersonTableColumns(), config)
        table.tabulateToConsole()
        expect(table.currentPageOrNull?.number).toBe(null)

        table.refresh()
        table.tabulateToConsole()

        table.loadNextPage()
        table.tabulateToConsole()

        table.loadNextPage()
        table.tabulateToConsole()
    }

    @Test
    fun should_be_able_to_select_table_items() {
        val config = ViewModelConfig(executor = SynchronousExecutor)
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator, config)
        val action = actionsOf(selector) {}
        val table = tableOf(paginator, selector, action, PersonTableColumns(), config)
        table.loadFirstPage()
        table.tabulateToConsole()

        table.select(row = 1)
        selector.select(row = 1)
        expect(table.isCurrentPageSelectedPartially()).toBe(true, "Table was supposed to be partially selected")
        table.tabulateToConsole()
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Explicit selector failed to select")
        expect(table.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Implicit selector failed to select")
    }

    @Test
    fun should_be_able_to_select_the_whole_current_page() {
        val config = ViewModelConfig(executor = SynchronousExecutor)
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator, config)
        val action = actionsOf(selector) {}
        val table = tableOf(paginator, selector, action, PersonTableColumns(), config)

        table.loadFirstPage()
        table.tabulateToConsole()

        table.selectAllItemsInTheCurrentPage()
        table.tabulateToConsole()
        expect(table.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Implicit selector failed to select")
    }

    @Test
    fun should_be_able_to_retrieve_primary_actions() {
        val config = ViewModelConfig(executor = SynchronousExecutor)
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator, config)
        val actions = actionsOf(selector) {
            primary {
                on("Create Person") { println("Just create da nigga") }
            }

            single {
                on("View ${it.name}") { println("Now viewing ${it.name}") }
            }
        }
        val table = tableOf(paginator, selector, actions, PersonTableColumns(), config)

        table.loadFirstPage()

        expect(table.actions).toBeOfSize(1)

        table.select(1)
        table.tabulateToConsole()

        expect(table.actions).toBeOfSize(2)
    }
}