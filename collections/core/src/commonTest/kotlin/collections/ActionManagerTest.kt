package collections

import expect.expect
import live.WatchMode
import live.watch
import presenters.collections.CollectionPaginator
import presenters.collections.SelectionManager
import presenters.collections.SinglePagePaginator
import presenters.collections.actionsOf
import presenters.collections.renderToConsole
import presenters.collections.tableOf
import kotlin.test.Test
import kotlin.test.fail

class ActionManagerTest {

    @Test
    fun should_not_crash_if_there_are_no_current_actions() {
        val pag = SinglePagePaginator(Person.List)
        val sel = SelectionManager(pag)
        val acts = actionsOf(sel) {}
        expect(acts.current.value).toBeEmpty()
    }

    @Test
    fun should_not_crash_if_there_is_a_selected_item_and_there_are_no_current_actions() {
        val pag = CollectionPaginator(Person.List)
        val sel = SelectionManager(pag)
        val actions = actionsOf(sel) {
            primary {
                onCreate { println("Create things") }
            }

            single {
                onEdit { println("Edit ${it.name}") }
            }
        }
        pag.loadFirstPage()
        val table = tableOf(pag, sel, actions, Person.columns())
        table.renderToConsole()
        expect(actions.current.value).toBeOfSize(1)
        sel.select(row = 1, page = 1)
        table.renderToConsole()
        expect(actions.current.value).toBeOfSize(2)
    }

    @Test
    fun should_add_actions_after_table_creations() {
        val pag = CollectionPaginator(Person.List)
        val sel = SelectionManager(pag)
        val actions = actionsOf(sel) {
            primary {
                onCreate { println("Create things") }
            }

            single {
                onEdit { println("Edit ${it.name}") }
            }
        }
        pag.loadFirstPage()
        val table = tableOf(pag, sel, actions, Person.columns())
        table.renderToConsole()
        expect(actions.current.value).toBeOfSize(1)
        sel.select(row = 1, page = 1)
        table.renderToConsole()
        expect(actions.current.value).toBeOfSize(2)
        sel.unSelectAllItemsInAllPages()
        table.manageActions { acts ->
            acts.addSingle("View") {
                println("Viewing ${it.name}")
            }
        }
        sel.select(row = 1, page = 1)
        expect(actions.current.value).toBeOfSize(3)
    }

    @Test
    fun should_delete_actions_after_table_creations() {
        val pag = CollectionPaginator(Person.List)
        val sel = SelectionManager(pag)
        val actions = actionsOf(sel) {
            primary {
                onCreate { println("Create things") }
            }

            single {
                onEdit { println("Edit ${it.name}") }
            }
        }
        pag.loadFirstPage()
        val table = tableOf(pag, sel, actions, Person.columns())
        table.manageActions { acts ->
            acts.remove("Create")
        }
        sel.select(row = 1, page = 1)
        expect(actions.current.value).toBeOfSize(1)
    }

    @Test
    fun should_only_display_current_multi_actions() {
        val pag = CollectionPaginator(Person.List)
        val sel = SelectionManager(pag)
        val actions = actionsOf(sel) {
            primary {
                onAdd { println("Add Person") }
            }
            multi {
                onDeleteAll { println("Delete ${it.size}") }
            }
        }
        pag.loadFirstPage()
        val table = tableOf(pag, sel, actions, Person.columns())
        expect(actions.current.value).toBeOfSize(1)
        sel.addSelection(1)
        sel.addSelection(2)
        expect(actions.current.value).toBeOfSize(2)
        sel.addSelection(3)
        sel.addSelection(4)
        expect(actions.current.value).toBeOfSize(2)
        actions.remove("Add")
        sel.addSelection(5)
        expect(actions.current.value).toBeOfSize(1)
    }
}