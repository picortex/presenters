package forms

import expect.expect
import expect.toBe
import kase.Failure
import kase.Submitting
import kase.Success
import kase.Validating
import kollections.toIList
import koncurrent.Later
import kotlinx.serialization.Serializable
import live.expect
import live.toHaveGoneThrough2
import live.toHaveGoneThrough3
import presenters.Option
import presenters.InputFieldState
import presenters.Fields
import presenters.name
import presenters.selectSingle
import kotlin.test.Test

class FormWithSingleSelectDropDownTest {
    @Serializable
    enum class Color {
        Red, Green, Blue
    }

    class TestFields : Fields() {
        val name = name(
            isRequired = true
        )

        val color = selectSingle(
            name = "color",
            items = Color.values().toIList(),
            mapper = { Option(it.name) },
            isRequired = true
        )
    }

    @Test
    fun should_fail_to_submit_when_a_required_select_single_is_not_selected() {
        val form = TestForm(TestFields()) {
            onSubmit { Later(0) }
        }

        form.fields.apply {
            name.type("John")
            color.clear()
        }

        form.submit()
        val (_, f) = expect(form.ui).toHaveGoneThrough2<Validating, Failure<Any?>>()
        expect(f.message).toBe("You have 1 invalid input")
        val err = expect(form.fields.color.feedback.value).toBe<InputFieldState.Error>()
        expect(err.message).toBe("Color is required")
    }

    @Test
    fun should_submit_when_a_required_select_single_has_been_selected() {
        val form = TestForm(TestFields()) {
            onSubmit { Later(0) }
        }

        form.fields.apply {
            name.type("John")
            color.selectItem(Color.Red)
        }

        form.submit()
        expect(form.ui).toHaveGoneThrough3<Validating, Submitting, Success<Any?>>()
    }
}