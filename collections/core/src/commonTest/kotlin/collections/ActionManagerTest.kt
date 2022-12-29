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

class ActionManagerTest {

    @Test
    fun should_not_crash_if_there_are_no_current_actions() {
        val pag = SinglePagePaginator(Person.List)
        val sel = SelectionManager(pag)
        val acts = actionsOf(sel) {}

        expect(acts.actions.value).toBeEmpty()
    }

    @Test
    fun should_not_crash_if_there_is_a_selected_item_and_there_are_no_current_actions() {
        val pag = CollectionPaginator(Person.List)
        val sel = SelectionManager(pag)
        val acts = actionsOf(sel) {
            primary {
                onCreate { println("Create things") }
            }

            single {
                onEdit { println("Edit ${it.name}") }
            }
        }
        pag.loadFirstPage()
        val table = tableOf(pag, sel, acts, Person.columns())
        table.renderToConsole()
        sel.state.watch(mode = WatchMode.Eagerly) {
            println("Selector: $it")
        }
        val watcher = acts.actions.watch(mode = WatchMode.Eagerly) {
            println("${it.size} Actions found")
        }
        expect(acts.actions.value).toBeOfSize(1)
        sel.select(row = 1, page = 1)
        expect(acts.actions.value).toBeOfSize(2)
        watcher.stop()
    }
}