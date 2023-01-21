import expect.expect
import presenters.MultiFileInputField
import presenters.SingleFileInputField
import kotlin.test.Test

class FileInputFieldTest {

    @Test
    fun should_set_a_file_on_input() {
        val input = SingleFileInputField(name = "image")

        input.set(FakeFileBlob())

        expect(input.data.value.output).toBe(FakeFileBlob())
    }

    @Test
    fun should_be_able_to_add_a_single_file_on_multi_file_input() {
        val input = MultiFileInputField(name = "image")
        input.add(FakeFileBlob())

        expect(input.data.value.output).toBeNonNull()

        val (file1) = input.data.value.output

        expect(file1).toBe(FakeFileBlob())
    }

    @Test
    fun should_be_able_to_add_a_many_files_on_multi_file_input() {
        val input = MultiFileInputField(name = "image")
        input.add(FakeFileBlob(name = "file1.tmp"))
        input.add(FakeFileBlob(name = "file2.tmp"))

        expect(input.data.value.output).toBeNonNull()

        val (file1, file2) = input.data.value.output

        expect(file1).toBe(FakeFileBlob(name = "file1.tmp"))
        expect(file2).toBe(FakeFileBlob(name = "file2.tmp"))
    }
}