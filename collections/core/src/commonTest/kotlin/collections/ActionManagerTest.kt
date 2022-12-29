package collections

import expect.expect
import presenters.collections.SelectionManager
import presenters.collections.SinglePagePaginator
import presenters.collections.actionsOf
import kotlin.test.Test

class ActionManagerTest {

    @Test
    fun should_not_crash_if_there_are_no_current_actions() {
        val pag = SinglePagePaginator(Person.List)
        val sel = SelectionManager(pag)
        val acts = actionsOf(sel) {}

        expect(acts.actions).toBeEmpty()
    }

    @Test
    fun should_not_crash_if_there_is_a_selected_item_and_there_are_no_current_actions() {
        val pag = SinglePagePaginator(Person.List)
        val sel = SelectionManager(pag)
        val acts = actionsOf(sel) {
            primary {
                onCreate { println("Create things") }
            }

            single {
                onEdit { println("Edit ${it.name}") }
            }
        }
//        sel.select(1)

        expect(acts.actions).toBeOfSize(1)
    }
}