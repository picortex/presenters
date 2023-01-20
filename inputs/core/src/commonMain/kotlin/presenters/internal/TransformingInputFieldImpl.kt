package presenters.internal

import kotlinx.serialization.KSerializer
import live.MutableLive
import live.mutableLiveOf
import presenters.fields.FormattedData
import presenters.fields.Formatter
import presenters.Label
import presenters.TransformingInputField
import presenters.fields.InputFieldState
import presenters.fields.internal.FormattedData
import presenters.internal.utils.Clearer
import presenters.internal.utils.DataTransformer
import presenters.internal.utils.FormattedOutputSetter
import presenters.internal.validators.CompoundValidator1
import presenters.internal.validators.LambdaValidator
import presenters.internal.validators.RequirementValidator

@PublishedApi
internal class TransformingInputFieldImpl<I : Any, O : Any>(
    override val name: String,
    override val isRequired: Boolean,
    override val label: Label,
    private val value: O?,
    override val hint: String,
    override val transformer: (I?) -> O?,
    override val serializer: KSerializer<O>,
    override val formatter: Formatter<O>?,
    override val isReadonly: Boolean,
    private val validator: ((O?) -> Unit)?,
) : TransformingInputField<I, O> {
    private val default = FormattedData(null, "", value)
    override val data: MutableLive<FormattedData<I, O>> = mutableLiveOf(default)
    override val feedback: MutableLive<InputFieldState> = mutableLiveOf(InputFieldState.Empty)

    private val tiv = CompoundValidator1(
        feedback,
        RequirementValidator(feedback, label.capitalizedWithoutAstrix(), isRequired),
        LambdaValidator(feedback, validator)
    )
    private val trnsfrmr = DataTransformer(formatter, transformer)
    private val setter = FormattedOutputSetter(data, feedback, trnsfrmr, tiv)

    override fun set(value: I) = setter.set(value)

    private val clearer = Clearer(default, data, feedback)
    override fun clear() = clearer.clear()

    override fun validate(value: O?) = tiv.validate(value)
    override fun validate() = tiv.validate(data.value.output)
    override fun validateSettingInvalidsAsErrors(value: O?) = tiv.validateSettingInvalidsAsErrors(value)
    override fun validateSettingInvalidsAsErrors() = tiv.validateSettingInvalidsAsErrors(data.value.output)
    override fun validateSettingInvalidsAsWarnings(value: O?) = tiv.validateSettingInvalidsAsWarnings(value)
    override fun validateSettingInvalidsAsWarnings() = tiv.validateSettingInvalidsAsWarnings(data.value.output)
}