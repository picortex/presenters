import expect.expect
import presenters.fields.MultiFileInputField
import presenters.fields.SingleFileInputField
import kotlin.test.Test

class FileInputFieldTest {

    @Test
    fun should_set_a_file_on_input() {
        val input = SingleFileInputField(name = "image")

        input.set(FakeFileBlob())

        expect(input.output.value).toBe(FakeFileBlob())
    }

    @Test
    fun should_be_able_to_add_a_single_file_on_multi_file_input() {
        val input = MultiFileInputField(name = "image")
        input.add(FakeFileBlob())

        expect(input.output.value).toBeNonNull()

        val (file1) = input.output.value ?: arrayOf()

        expect(file1).toBe(FakeFileBlob())
    }

    @Test
    fun should_be_able_to_add_a_many_files_on_multi_file_input() {
        val input = MultiFileInputField(name = "image")
        input.add(FakeFileBlob(name = "file1.tmp"))
        input.add(FakeFileBlob(name = "file2.tmp"))

        expect(input.output.value).toBeNonNull()

        val (file1, file2) = input.output.value ?: arrayOf()

        expect(file1).toBe(FakeFileBlob(name = "file1.tmp"))
        expect(file2).toBe(FakeFileBlob(name = "file2.tmp"))
    }
}