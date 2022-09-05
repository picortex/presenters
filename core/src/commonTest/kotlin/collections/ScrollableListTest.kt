package collections

import expect.expect
import koncurrent.SynchronousExecutor
import kotlinx.coroutines.test.runTest
import presenters.collections.*
import live.*
import live.expect
import logging.ConsoleAppender
import logging.LogLevel
import logging.Logger
import presenters.collections.CollectionPaginator
import presenters.collections.PageableState.LoadedPage
import presenters.collections.PageableState.Loading
import viewmodel.ViewModelConfig
import kotlin.test.Test

class ScrollableListTest {

    @Test
    fun can_be_assigned_a_paginator() {

        val config = ViewModelConfig(executor = SynchronousExecutor)
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator, config)
        val actions = actionsOf(selector) {}

        val list = scrollableListOf(paginator, selector, actions, config)

        expect(list.currentPageOrNull?.number).toBe(null)

        list.refresh()
        val (loading) = expect(paginator.live).toHaveGoneThrough2<Loading<*>, LoadedPage<*>>()
        expect(loading.message).toBe("Loading")

        list.loadNextPage()

        list.loadNextPage()
    }

    @Test
    fun should_be_able_to_select_table_items() {
        val config = ViewModelConfig(executor = SynchronousExecutor)
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator, config)
        val actions = actionsOf(selector) {}

        val list = scrollableListOf(paginator, selector, actions, config)

        list.loadFirstPage()

        list.select(row = 1)
        selector.select(row = 1)
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Explicit selector failed to select")
        expect(list.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Implicit selector failed to select")
    }

    @Test
    fun should_be_able_to_select_the_whole_current_page() {
        val config = ViewModelConfig(executor = SynchronousExecutor)
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator, config)
        val actions = actionsOf(selector) {}

        val list = scrollableListOf(paginator, selector, actions, config)

        list.loadFirstPage()

        list.selectAllItemsInTheCurrentPage()
        expect(list.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Implicit selector failed to select")
    }

    @Test
    fun should_be_able_to_retrieve_primary_actions() {
        val config = ViewModelConfig(executor = SynchronousExecutor)
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator, config)
        val actions = actionsOf(selector) {
            primary {
                on("Create Person") { println("Creating Person") }
            }

            single {
                if (!it.name.contains("Admin")) {
                    on("Convert ${it.name} to Admin") { println("Converting ${it.name} into an admin") }
                } else {
                    on("Remove from admins") { println("Remove ${it.name} from the group of admins") }
                }
            }

            multi {
                on("Delete ${it.size} people?") { println("Deleting ${it.size} people") }
            }

            global {
                on("Send Adverts") { println("Send adverts") }
            }
        }

        val list = scrollableListOf(paginator, selector, actions, config)

        list.loadFirstPage()
        list.select(row = 1)

        expect(list.actions).toBeOfSize(2)
    }

    @Test
    fun should_be_able_to_load_more_data() = runTest {
        val config = ViewModelConfig(executor = SynchronousExecutor, logger = Logger())
        val paginator = CollectionPaginator(Person.List, capacity = 6)
        val selector = SelectionManager(paginator, config)
        val actions = actionsOf(selector) {}
        val list = scrollableListOf(paginator, selector, actions, config)

        list.loadFirstPage()
        expect(list.items.value).toBeOfSize(6)

        list.loadNextPage()
        expect(list.items.value).toBeOfSize(12)

        list.refresh()
        expect(list.items.value).toBeOfSize(12)

        list.loadNextPage()
        expect(list.items.value).toBeOfSize(18)
    }
}