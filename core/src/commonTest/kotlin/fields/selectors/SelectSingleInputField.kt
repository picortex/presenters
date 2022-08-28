package fields.selectors

import expect.expect
import kotlinx.collections.interoperable.toInteroperableList
import presenters.fields.Option
import presenters.fields.SelectSingleInputField
import kotlin.test.Test
import kotlin.test.fail

class SelectSingleInputFieldTest {
    enum class Color {
        Red, Green, Blue
    }

    @Test
    fun should_be_able_to_select_a_single_item() {
        val color = SelectSingleInputField(
            name = "color",
            items = Color.values().toInteroperableList(),
            mapper = { Option(label = it.name) }
        )
        color.selectValue("Red")
        expect(color.value).toBe(Color.Red)
    }

    @Test
    fun should_be_able_to_select_an_item_by_providing_an_item_itself() {
        val color = SelectSingleInputField(
            name = "color",
            items = Color.values().toInteroperableList(),
            mapper = { Option(label = it.name) }
        )
        color.selectedItem(Color.Green)
        expect(color.value).toBe(Color.Green)
    }

    @Test
    fun should_be_able_to_unselect_an_item() {
        val color = SelectSingleInputField(
            name = "color",
            items = Color.values().toInteroperableList(),
            mapper = { Option(label = it.name) }
        )
        color.selectedItem(Color.Green)
        expect(color.value).toBe(Color.Green)

        color.unselect()

        expect(color.value).toBe(null)
    }

    @Test
    fun should_provide_error_feedback_if_the_field_is_unrequired() {
        val color = SelectSingleInputField(
            name = "color",
            items = Color.values().toInteroperableList(),
            mapper = { Option(label = it.name) },
            isRequired = true
        )

        color.selectedItem(Color.Green)
        expect(color.value).toBe(Color.Green)

        color.unselect()
        try {
            color.validate()
            fail()
        } catch (err: Throwable) {
            expect(err.message).toBe("Color is required")
        }
    }
}