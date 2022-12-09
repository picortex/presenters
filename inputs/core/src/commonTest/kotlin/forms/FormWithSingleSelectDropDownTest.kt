package forms

import expect.expect
import expect.toBe
import kollections.toIList
import koncurrent.Later
import kotlinx.serialization.Serializable
import live.expect
import live.toHaveGoneThrough2
import live.toHaveGoneThrough3
import presenters.fields.InputFieldState
import presenters.fields.Option
import presenters.forms.Fields
import presenters.forms.FormState.*
import presenters.forms.fields.name
import presenters.forms.fields.selectSingle
import kotlin.test.Test

class FormWithSingleSelectDropDownTest {
    @Serializable
    enum class Color {
        Red, Green, Blue
    }

    class TestFields : Fields() {
        val name by name(
            isRequired = true
        )

        val color by selectSingle(
            items = Color.values().toIList(),
            mapper = { Option(it.name) },
            isRequired = true
        )
    }

    @Test
    fun should_fail_to_submit_when_a_required_select_single_is_not_selected() {
        val form = TestForm(TestFields()) {
            onSubmit { Later.resolve(0) }
        }

        form.fields.apply {
            name.type("John")
            color.clear()
        }

        form.submit()
        val (_, f) = expect(form.ui).toHaveGoneThrough2<Validating, Failure>()
        expect(f.message).toBe("You have 1 invalid input")
        val err = expect(form.fields.color.feedback.value).toBe<InputFieldState.Error>()
        expect(err.message).toBe("Color is required")
    }

    @Test
    fun should_submit_when_a_required_select_single_has_been_selected() {
        val form = TestForm(TestFields()) {
            onSubmit { Later.resolve(0) }
        }

        form.fields.apply {
            name.type("John")
            color.selectItem(Color.Red)
        }

        form.submit()
        expect(form.ui).toHaveGoneThrough3<Validating, Submitting, Submitted>()
    }
}