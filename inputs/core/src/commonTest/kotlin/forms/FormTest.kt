package forms

import expect.expect
import koncurrent.Later
import kotlinx.coroutines.test.runTest
import live.expect
import live.toHaveGoneThrough2
import live.toHaveGoneThrough3
import presenters.forms.*
import presenters.forms.FormState.*
import presenters.forms.fields.text
import kotlin.test.Ignore
import kotlin.test.Test

class FormTest {

    class PersonFields : Fields() {
        val name by text(isRequired = true)
        val details by text()
    }

    @Test
    fun should_easily_interact_with_text_fields() {
        val fields = PersonFields()
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
    @Ignore // TODO: Fix this if you are finished with migration
    fun person_form_should_be_able_to_recover_after_failure() = runTest {
        val form = TestForm(PersonFields()) {
            onSubmit {
                println(it.entries.joinToString { entry -> "${entry.key}=${entry.value}" })
                Later.resolve(Unit)
            }
        }

        form.fields.apply {
            name.value = "Anderson"
            details.value = "andy@lamax"
        }
        form.submit()
        val (_, s1) = expect(form.ui).toHaveGoneThrough2<Validating, Failure>()
        expect(s1.message).toBe("You have 1 invalid input")

        form.ui.history.clear()

        form.fields.apply {
            name.value = null
            details.value = "andy@lamax"
        }
        form.submit()
        val (_, s2) = expect(form.ui).toHaveGoneThrough2<Validating, Failure>()
        expect(s2.message).toBe("You have 2 invalid inputs")

        form.ui.history.clear()
        form.fields.apply {
            name.value = "Anderson"
            details.value = "andy@lamax.me"
        }
        form.submit()
        expect(form.fields.details.value).toBe("andy@lamax.me")
        expect(form.ui).toHaveGoneThrough3<Validating, Submitting, Submitted>()
    }
}