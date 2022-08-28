package fields

import expect.expect
import presenters.forms.Fields
import presenters.forms.fields.number
import presenters.forms.fields.text
import kotlin.test.Test

class FieldsTest {

    class TestFormFields : Fields() {
        val name by text()
        val age by number()
    }

    @Test
    fun should_easily_interact_with_text_fields() {
        val fields = TestFormFields()
        val name = fields.name
        name.apply {
            value = "A"
            value = "An"
            value = "And"
            value = "Anderson"
        }
        expect(name.value).toBe("Anderson")
    }
}