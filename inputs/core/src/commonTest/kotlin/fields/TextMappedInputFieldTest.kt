package fields

import expect.expect
import koncurrent.Later
import koncurrent.later.await
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import presenters.Fields
import presenters.Form
import presenters.FormActionsBuildingBlock
import presenters.toFormConfig
import presenters.textTo
import viewmodel.ViewModelConfig
import kotlin.test.Test

class TextMappedInputFieldTest {
    @Serializable
    data class Category(val uid: String = "unset", val name: String)

    class TestFields : Fields() {
        val category = textTo(name = "Category") {
            Category(name = it ?: "Nothing")
        }
    }

    class TestForm(initializer: FormActionsBuildingBlock<JsonObject, Any?>) : Form<TestFields, JsonObject, Any?>(
        heading = "Test Form",
        details = "A Form for testing TextMappedInputField",
        fields = TestFields(),
        config = ViewModelConfig().toFormConfig(),
        initializer = initializer
    )

    @Test
    fun should_test_people() = runTest {
        val form = TestForm {
            onSubmit {
                println("Submitted with : ${it.toMap()}")
                Later(Unit)
            }
        }
        form.fields.category.set("Test")
        expect(form.fields.category.data.value.output).toBe(Category(name = "Test"))
        form.submit().await()
    }
}