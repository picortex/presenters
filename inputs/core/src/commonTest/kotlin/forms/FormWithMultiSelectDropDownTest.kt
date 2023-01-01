package forms

import expect.expect
import expect.toBe
import kase.Failure
import kase.Submitting
import kase.Success
import kase.Validating
import kollections.toIList
import koncurrent.Later
import kotlinx.collections.interoperable.iListOf
import kotlinx.serialization.Serializable
import live.expect
import live.toHaveGoneThrough2
import live.toHaveGoneThrough3
import presenters.fields.InputFieldState
import presenters.fields.Option
import presenters.forms.Fields
import presenters.forms.fields.name
import presenters.forms.fields.selectMany
import kotlin.test.Ignore
import kotlin.test.Test

class FormWithMultiSelectDropDownTest {
    @Serializable
    enum class Color {
        Red, Green, Blue
    }

    class TestFields : Fields() {
        val name = name(
            isRequired = true
        )

        val color = selectMany(
            name = "color",
            items = Color.values().toIList(),
            mapper = { Option(it.name) },
            isRequired = true
        )
    }

    @Test
    fun should_fail_to_submit_when_a_required_nothing_has_been_selected() {
        val form = TestForm(TestFields()) {
            onSubmit { Later(0) }
        }

        form.fields.apply {
            name.type("John")
            color.clear()
        }

        form.submit()
        val (_, f) = expect(form.ui).toHaveGoneThrough2<Validating, Failure<*>>()
        expect(f.message).toBe("You have 1 invalid input")
        val err = expect(form.fields.color.feedback.value).toBe<InputFieldState.Error>()
        expect(err.message).toBe("Color is required")
    }

    @Test
    @Ignore // TODO: Define proper params that would accommodate color here
    fun should_submit_when_a_required_select_multi_has_been_selected() {
        val form = TestForm(TestFields()) {
            onSubmit { Later(0) }
        }

        form.fields.apply {
            name.type("John")
            color.addSelectedItem(Color.Blue)
        }

        form.submit()
        expect(form.ui).toHaveGoneThrough3<Validating, Submitting, Success<*>>()
    }

    @Test
    @Ignore // TODO: Define proper params that would accommodate color here
    fun should_submit_when_a_required_select_multi_has_been_selected_with_multiple_inputs() {
        val form = TestForm(TestFields()) {
            onSubmit { Later(0) }
        }

        form.fields.apply {
            name.type("John")
            color.addSelectedItem(Color.Blue)
            color.addSelectedItem(Color.Red)
        }

        form.submit()
        expect(form.ui).toHaveGoneThrough3<Validating, Submitting, Success<*>>()
        expect(form.fields.color.output.value).toBe(iListOf(Color.Red, Color.Blue))
    }
}