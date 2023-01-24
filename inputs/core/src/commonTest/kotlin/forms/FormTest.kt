package forms

import expect.expect
import kase.Failure
import kase.Submitting
import kase.Success
import kase.Validating
import koncurrent.Later
import kotlinx.coroutines.test.runTest
import live.expect
import live.toHaveGoneThrough2
import live.toHaveGoneThrough3
import presenters.Fields
import presenters.name
import presenters.text
import kotlin.test.Test

class FormTest {

    class PersonFields : Fields() {
        val name = name(isRequired = true)
        val details = text(name = "details")
        val address = text("address", isRequired = true)
    }

    @Test
    fun should_easily_interact_with_text_fields() {
        val fields = PersonFields()
        val name = fields.name
        name.type("Anderson")
        expect(name.data.value.output).toBe("Anderson")
    }

    @Test
    fun forms_should_be_able_to_clear_clearable_inputs() {
        val form = TestForm(PersonFields()) {
            onSubmit {
                println(it.entries.joinToString { entry -> "${entry.key}=${entry.value}" })
                Later(Unit)
            }
        }
        form.fields.apply {
            name.type("Anderson")
        }

        expect(form.fields.name.data.value.output).toBe("Anderson")
        form.clear()
        expect(form.fields.name.data.value.output).toBe(null)
    }

    @Test
    fun person_form_should_be_able_to_recover_after_failure() = runTest {
        val form = TestForm(PersonFields()) {
            onSubmit {
                println(it.entries.joinToString { entry -> "${entry.key}=${entry.value}" })
                Later(Unit)
            }
        }

        form.fields.apply {
            name.type("Anderson")
            details.type("andy@lamax")
        }
        form.submit()
        val (_, s1) = expect(form.ui).toHaveGoneThrough2<Validating, Failure<Any?>>()
        expect(s1.message).toBe("You have 1 invalid input")

        form.clear()

        form.fields.apply {
            name.clear()
            details.type("andy@lamax")
        }
        form.submit()
        val (_, s2) = expect(form.ui).toHaveGoneThrough2<Validating, Failure<Any?>>()
        expect(s2.message).toBe("You have 2 invalid inputs")

        form.clear()

        form.fields.apply {
            name.type("Anderson")
            details.type("andy@lamax.me")
            address.type("13")
        }
        form.submit()
        expect(form.fields.details.data.value.output).toBe("andy@lamax.me")
        expect(form.ui).toHaveGoneThrough3<Validating, Submitting, Success<Any?>>()
    }
}