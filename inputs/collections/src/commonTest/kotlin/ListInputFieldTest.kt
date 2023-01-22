import expect.expect
import presenters.ListInputField
import kotlin.test.Test

class ListInputFieldTest {

    @Test
    fun should_be_able_to_add_items_to_the_list() {
        val input = ListInputField<String>("items")
        input.add("S1")
        expect(input.data.value.output).toContain("S1")
    }

    @Test
    fun should_be_able_to_remove_items_from_the_list() {
        val input = ListInputField<String>(name = "items")
        input.add("S1")
        input.add("S2")
        input.add("S3")
        expect(input.data.value.output).toContain("S1", "S2", "S3")

        input.remove("S1")
        expect(input.data.value.output).toContain("S2", "S3")
    }
}