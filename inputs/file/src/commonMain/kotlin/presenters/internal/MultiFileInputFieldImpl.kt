package presenters.internal

import epsilon.FileBlob
import epsilon.serializers.FileBlobSerializer
import kollections.List
import kollections.iEmptyList
import kollections.serializers.ListSerializer
import kollections.toIList
import kotlinx.serialization.KSerializer
import live.MutableLive
import live.mutableLiveOf
import presenters.InputFieldState
import presenters.Label
import presenters.MultiFileInputField
import presenters.OutputData
import presenters.internal.utils.Clearer
import presenters.internal.utils.OutputSetter
import presenters.internal.validators.CompoundValidator
import presenters.internal.validators.LambdaValidator
import presenters.internal.validators.RequirementValidator

@PublishedApi
internal class MultiFileInputFieldImpl(
    override val name: String,
    override val label: Label,
    override val hint: String,
    private val value: List<FileBlob>?,
    override val isReadonly: Boolean,
    override val isRequired: Boolean,
    private val validator: ((List<FileBlob>?) -> Unit)?,
) : MultiFileInputField {
    private val default = OutputList(value)
    override val data = mutableLiveOf(default)
    override val serializer: KSerializer<List<FileBlob>> = SERIALIZER
    override val feedback: MutableLive<InputFieldState> = mutableLiveOf(InputFieldState.Empty)

    private val sfv = CompoundValidator(
        data, feedback,
        RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        LambdaValidator(data, feedback, validator)
    )

    private val setter = OutputSetter(data as MutableLive<OutputData<List<FileBlob>>>, feedback, sfv)
    override fun set(value: List<FileBlob>) = setter.set(value)

    private val output get() = data.value.output
    override fun add(file: FileBlob) = set((output + file).toIList())

    override fun addAll(files: List<FileBlob>) = set((output + files).toIList())

    private val cleaner = Clearer(default, data, feedback)
    override fun clear() = cleaner.clear()

    override fun remove(file: FileBlob) = set((output - file).toIList())

    override fun removeAll() = set(iEmptyList())

    override fun validate(value: List<FileBlob>?) = sfv.validate(value)
    override fun validateSettingInvalidsAsErrors(value: List<FileBlob>?) = sfv.validateSettingInvalidsAsErrors()
    override fun validateSettingInvalidsAsWarnings(value: List<FileBlob>?) = sfv.validateSettingInvalidsAsWarnings()

    private companion object {
        private val SERIALIZER: KSerializer<List<FileBlob>> = ListSerializer(FileBlobSerializer)
    }
}