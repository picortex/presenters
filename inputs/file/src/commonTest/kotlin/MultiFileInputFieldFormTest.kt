import epsilon.FileBlob
import expect.expect
import kollections.List
import koncurrent.Later
import kotlinx.serialization.Serializable
import presenters.files
import presenters.Fields
import presenters.Form
import presenters.name
import presenters.toFormConfig
import viewmodel.ViewModelConfig
import kotlin.test.Test

class MultiFileInputFieldFormTest {
    @Serializable
    data class TestParams(
        val name: String,
        val images: List<FileBlob>
    )

    class TestFields : Fields() {
        val name = name(name = TestParams::name)
        val image = files(name = TestParams::images)
    }

    @Test
    fun should_actual_return_a_file_back() {
        var params: TestParams? = null
        val form = Form<TestFields, TestParams, TestParams>(
            heading = "File Input Test Form",
            details = "Single file test input form",
            config = ViewModelConfig().toFormConfig(),
            fields = TestFields()
        ) {
            onCancel { println("Cancelled called") }
            onSubmit {
                params = it
                Later(it)
            }
        }

        form.fields.apply {
            name.type("coke")
            image.add(FakeFileBlob(name = "coke1.png"))
        }

        form.submit()
        expect(params?.name).toBe("coke")
        expect(params?.images).toContainElements()
        expect(params?.images?.firstOrNull()?.name).toBe("coke1.png")
    }
}