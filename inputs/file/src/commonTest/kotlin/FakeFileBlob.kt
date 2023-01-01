import epsilon.FileBlob
import koncurrent.Executor
import koncurrent.Later

data class FakeFileBlob(
    override val path: String = "file://fake/test.fake",
    override val name: String = "test.fake"
) : FileBlob {
    override fun readBytes(executor: Executor) = Later(path.encodeToByteArray())
}