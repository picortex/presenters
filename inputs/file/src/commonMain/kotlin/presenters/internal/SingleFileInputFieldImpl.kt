package presenters.internal

import epsilon.FileBlob
import epsilon.serializers.FileBlobSerializer
import kotlinx.serialization.KSerializer
import live.MutableLive
import live.mutableLiveOf
import presenters.InputFieldState
import presenters.Label
import presenters.SingleFileInputField
import presenters.internal.utils.Clearer
import presenters.internal.utils.OutputSetter
import presenters.internal.validators.CompoundValidator
import presenters.internal.validators.LambdaValidator
import presenters.internal.validators.RequirementValidator

@PublishedApi
internal class SingleFileInputFieldImpl(
    override val name: String,
    override val label: Label,
    override val hint: String,
    private val value: FileBlob?,
    override val isReadonly: Boolean,
    override val isRequired: Boolean,
    private val validator: ((FileBlob?) -> Unit)?
) : PlainDataField<FileBlob>(value), SingleFileInputField {
    override val serializer: KSerializer<FileBlob> = FileBlobSerializer
    override val cv = CompoundValidator(
        data, feedback,
        RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        LambdaValidator(data, feedback, validator)
    )
}