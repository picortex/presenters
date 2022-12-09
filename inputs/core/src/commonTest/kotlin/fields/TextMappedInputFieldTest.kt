package fields

import koncurrent.Later
import koncurrent.later.await
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import live.expect
import presenters.forms.Fields
import presenters.forms.Form
import presenters.forms.FormActionsBuildingBlock
import presenters.forms.fields.textTo
import presenters.forms.toFormConfig
import viewmodel.ViewModelConfig
import kotlin.test.Test

class TextMappedInputFieldTest {
    @Serializable
    data class Category(val uid: String = "unset", val name: String)
    class TestFields : Fields() {
        val category by textTo(name = "Category") {
            Category(name = it ?: "Nothing")
        }
    }

    class TestForm(initializer: FormActionsBuildingBlock<JsonObject>) : Form<TestFields, JsonObject>(
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
                Later.resolve(Unit)
            }
        }
        form.fields.category.type("Test")
        expect(form.fields.category.field).toHaveGoneThrough(Category(name = "Test"))
        form.submit().await()
    }
}