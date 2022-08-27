package forms

import expect.expect
import koncurrent.Later
import koncurrent.later.catch
import koncurrent.later.then
import presenters.forms.Fields
import presenters.forms.fields.number
import presenters.forms.fields.text
import kotlin.test.Test

class BaseFormTest {

    class TestFormFields : Fields() {
        val name by text()
        val age by number()
    }

    @Test
    fun should_easily_interact_with_text_fields() {
        val fields = TestFormFields()
        val name = fields.name
        name.apply {
            value = "A"
            value = "An"
            value = "And"
            value = "Anderson"
        }
        expect(name.value).toBe("Anderson")
    }

    @Test
    fun later_test_1() {
        Later.resolve(2).catch {
            println("Should not be executed")
            5
        }.then {
            println(it)
            println("Should be executed")
        }
    }

    @Test
    fun later_test_2() {
        Later.resolve(2).then(
            onRejected = {
                println("Should not be executed")
                5
            },
            onResolved = {
                println(it)
                println("Should be executed")
            }
        )
    }
}