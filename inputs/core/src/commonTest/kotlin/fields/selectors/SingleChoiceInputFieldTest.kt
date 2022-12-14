package fields.selectors

import expect.expect
import kollections.toIList
import kotlinx.serialization.Serializable
import presenters.fields.Invalid
import presenters.fields.Option
import presenters.fields.SingleChoiceValuedField
import kotlin.test.Test

class SingleChoiceInputFieldTest {
    @Serializable
    enum class Color {
        Red, Green, Blue
    }

    @Test
    fun should_be_able_to_select_a_single_item() {
        val color = SingleChoiceValuedField(
            name = "color",
            items = Color.values().toIList(),
            mapper = { Option(label = it.name) },
            serializer = Color.serializer()
        )
        color.selectValue("Red")
        expect(color.output.value).toBe(Color.Red)
    }

    @Test
    fun should_be_able_to_select_an_item_by_providing_an_item_itself() {
        val color = SingleChoiceValuedField(
            name = "color",
            items = Color.values().toIList(),
            mapper = { Option(label = it.name) },
            serializer = Color.serializer()
        )
        color.selectItem(Color.Green)
        expect(color.output.value).toBe(Color.Green)
    }

    @Test
    fun should_be_able_to_unselect_an_item() {
        val color = SingleChoiceValuedField(
            name = "color",
            items = Color.values().toIList(),
            mapper = { Option(label = it.name) },
            serializer = Color.serializer()
        )
        color.selectItem(Color.Green)
        expect(color.output.value).toBe(Color.Green)

        color.unselect()

        expect(color.output.value).toBe(null)
    }

    @Test
    fun should_provide_error_feedback_if_the_field_is_unrequired() {
        val color = SingleChoiceValuedField(
            name = "color",
            items = Color.values().toIList(),
            mapper = { Option(label = it.name) },
            serializer = Color.serializer(),
            isRequired = true
        )

        color.selectItem(Color.Green)
        expect(color.output.value).toBe(Color.Green)

        color.unselect()
        val res = color.validate() as Invalid
        expect(res.cause.message).toBe("Color is required")
    }

    @Test
    fun should_have_no_null_options() {
        val color = SingleChoiceValuedField(
            name = "color",
            items = Color.values().toIList(),
            mapper = { Option(label = it.name) },
            serializer = Color.serializer()
        )

        expect(color.options(withSelect = false)).toBeOfSize(3)
        expect(color.options(withSelect = true)).toBeOfSize(4)

        val options = color.options(withSelect = true)
        println(options)
        expect(options[1].value).toBe("Red")
        expect(options[2].value).toBe("Green")
        expect(options[3].value).toBe("Blue")
    }
}