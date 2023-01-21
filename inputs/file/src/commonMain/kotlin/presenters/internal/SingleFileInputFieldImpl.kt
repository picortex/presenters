package presenters.internal

import epsilon.FileBlob
import epsilon.serializers.FileBlobSerializer
import kotlinx.serialization.KSerializer
import live.Live
import live.MutableLive
import live.mutableLiveOf
import presenters.InputFieldState
import presenters.Label
import presenters.OutputData
import presenters.SingleFileInputField
import presenters.fields.SingleValuedField
import presenters.internal.utils.Clearer
import presenters.internal.utils.FormattedOutputSetter
import presenters.internal.utils.OutputSetter
import presenters.internal.validators.CompoundValidator
import presenters.internal.validators.LambdaValidator
import presenters.internal.validators.RequirementValidator
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.Validateable
import presenters.validation.ValidationResult

@PublishedApi
internal class SingleFileInputFieldImpl(
    override val name: String,
    override val label: Label,
    override val hint: String,
    private val value: FileBlob?,
    override val isReadonly: Boolean,
    override val isRequired: Boolean,
    private val validator: ((FileBlob?) -> Unit)?
) : SingleFileInputField {
    private val default = OutputData(value)
    override val data = mutableLiveOf(default)
    override val serializer: KSerializer<FileBlob> = FileBlobSerializer
    override val feedback: MutableLive<InputFieldState> = mutableLiveOf(InputFieldState.Empty)

    private val sfv = CompoundValidator(
        data, feedback,
        RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        LambdaValidator(data, feedback, validator)
    )
    private val setter = OutputSetter(data, feedback, sfv)
    override fun set(value: FileBlob) = setter.set(value)

    private val clearer = Clearer(default, data, feedback)
    override fun clear() = clearer.clear()

    override fun validate(value: FileBlob?) = sfv.validate(value)
    override fun validateSettingInvalidsAsErrors(value: FileBlob?) = sfv.validateSettingInvalidsAsErrors(value)
    override fun validateSettingInvalidsAsWarnings(value: FileBlob?) = sfv.validateSettingInvalidsAsWarnings(value)
}