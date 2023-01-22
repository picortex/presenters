import epsilon.FileBlob
import expect.expect
import koncurrent.Later
import kotlinx.serialization.Serializable
import presenters.file
import presenters.Fields
import presenters.Form
import presenters.toFormConfig
import presenters.name
import viewmodel.ViewModelConfig
import kotlin.test.Test

class SingleFileInputFieldFormTest {
    @Serializable
    data class TestParams(
        val name: String,
        val image: FileBlob
    )

    class TestFields : Fields() {
        val name = name(name = TestParams::name)
        val image = file(name = TestParams::image)
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
            image.set(FakeFileBlob(name = "coke.png"))
        }

        form.submit()
        expect(params?.name).toBe("coke")
        expect(params?.image?.name).toBe("coke.png")
    }
}