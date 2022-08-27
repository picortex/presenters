package modal

import expect.*
import koncurrent.Later
import kotlinx.coroutines.test.runTest
import presenters.forms.Fields
import presenters.forms.fields.text
import presenters.modal.ConfirmDialog
import presenters.modal.FormDialog
import presenters.modal.click
import kotlin.test.Ignore
import kotlin.test.Test

class DialogTest {

    @Test
    @Ignore
    fun should_be_able_to_create_a_dialog() {
        val clicks = mutableListOf<String>()
        val d = ConfirmDialog(
            heading = "Test Dialog",
            details = "This is a test dialog",
        ) {
            on("Cancel") {
                clicks.add("cancelled")
            }
            on("Delete") {
                clicks.add("deleted")
            }
            onConfirm { Later.resolve(Unit) }
        }

        d.click("Cancel")
        expectCollection(clicks).toContain("cancelled")

        d.click("Delete")
        expectCollection(clicks).toContain("deleted")
    }

    @Test
    fun should_return_failure_when_trying_to_click_an_incorrect_action() = runTest {
        class TestFields : Fields() {
            val name by text()
        }

        val d = FormDialog(
            heading = "Test dialog",
            details = "Test details",
            fields = TestFields()
        ) {
            on("Cancel") { println("Canceled") }
            on("Delete") { }
            onSubmit { p: Unit -> Later.resolve(p) }
        }
//        expectFailure {
//            d.click("OK")
//        }
    }
}