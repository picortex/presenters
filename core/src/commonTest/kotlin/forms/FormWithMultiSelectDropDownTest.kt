package forms

import expect.expect
import expect.toBe
import koncurrent.Later
import kotlinx.collections.interoperable.iListOf
import kotlinx.serialization.Serializable
import live.expect
import live.toHaveGoneThrough2
import live.toHaveGoneThrough3
import presenters.fields.InputFieldState
import presenters.fields.Option
import presenters.forms.Fields
import presenters.forms.FormState.*
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
        val name by name(
            isRequired = true
        )

        val color by selectMany(
            items = Color.values().toList(),
            mapper = { Option(it.name) },
            isRequired = true
        )
    }

    @Test
    fun should_fail_to_submit_when_a_required_nothing_has_been_selected() {
        val form = TestForm(TestFields()) {
            onSubmit { Later.resolve(0) }
        }

        form.fields.apply {
            name.value = "John"
            color.value = null
        }

        form.submit()
        val (_, f) = expect(form.ui).toHaveGoneThrough2<Validating, Failure>()
        expect(f.message).toBe("You have 1 invalid input")
        val err = expect(form.fields.color.feedback.value).toBe<InputFieldState.Error>()
        expect(err.message).toBe("color is required")
    }

    @Test
    @Ignore // TODO: Define proper params that would accommodate color here
    fun should_submit_when_a_required_select_multi_has_been_selected() {
        val form = TestForm(TestFields()) {
            onSubmit { Later.resolve(0) }
        }

        form.fields.apply {
            name.value = "John"
            color.addSelectedItem(Color.Blue)
        }

        form.submit()
        expect(form.ui).toHaveGoneThrough3<Validating, Submitting, Submitted>()
    }

    @Test
    @Ignore // TODO: Define proper params that would accommodate color here
    fun should_submit_when_a_required_select_multi_has_been_selected_with_multiple_inputs() {
        val form = TestForm(TestFields()) {
            onSubmit { Later.resolve(0) }
        }

        form.fields.apply {
            name.value = "John"
            color.addSelectedItem(Color.Blue)
            color.addSelectedItem(Color.Red)
        }

        form.submit()
        expect(form.ui).toHaveGoneThrough3<Validating, Submitting, Submitted>()
        expect(form.fields.color.value).toBe(iListOf(Color.Red, Color.Blue))
    }
}