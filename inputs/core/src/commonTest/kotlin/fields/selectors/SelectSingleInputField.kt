package fields.selectors

import expect.expect
import kollections.toIList
import kotlinx.collections.interoperable.toInteroperableList
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.nullable
import presenters.fields.Invalid
import presenters.fields.Option
import presenters.fields.SelectSingleInputField
import kotlin.test.Test
import kotlin.test.fail

class SelectSingleInputFieldTest {
    @Serializable
    enum class Color {
        Red, Green, Blue
    }

    @Test
    fun should_be_able_to_select_a_single_item() {
        val color = SelectSingleInputField(
            name = "color",
            items = Color.values().toIList(),
            mapper = { Option(label = it.name) },
            serializer = Color.serializer().nullable
        )
        color.selectValue("Red")
        expect(color.output.value).toBe(Color.Red)
    }

    @Test
    fun should_be_able_to_select_an_item_by_providing_an_item_itself() {
        val color = SelectSingleInputField(
            name = "color",
            items = Color.values().toIList(),
            mapper = { Option(label = it.name) },
            serializer = Color.serializer().nullable
        )
        color.selectItem(Color.Green)
        expect(color.output.value).toBe(Color.Green)
    }

    @Test
    fun should_be_able_to_unselect_an_item() {
        val color = SelectSingleInputField(
            name = "color",
            items = Color.values().toIList(),
            mapper = { Option(label = it.name) },
            serializer = Color.serializer().nullable
        )
        color.selectItem(Color.Green)
        expect(color.output.value).toBe(Color.Green)

        color.unselect()

        expect(color.output.value).toBe(null)
    }

    @Test
    fun should_provide_error_feedback_if_the_field_is_unrequired() {
        val color = SelectSingleInputField(
            name = "color",
            items = Color.values().toIList(),
            mapper = { Option(label = it.name) },
            serializer = Color.serializer().nullable,
            isRequired = true
        )

        color.selectItem(Color.Green)
        expect(color.output.value).toBe(Color.Green)

        color.unselect()
        val res = color.validate() as Invalid
        expect(res.cause.message).toBe("Color is required")
    }
}