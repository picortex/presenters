package presenters.internal.numbers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import live.MutableLive
import live.mutableLiveOf
import presenters.BooleanInputField
import presenters.Label
import presenters.fields.InputFieldState
import presenters.fields.internal.OutputData
import presenters.internal.utils.Clearer
import presenters.internal.utils.OutputSetter
import presenters.internal.validators.CompoundValidator1
import presenters.internal.validators.LambdaValidator
import presenters.internal.validators.RequirementValidator

@PublishedApi
internal class BooleanInputFieldImpl(
    override val name: String,
    override val isRequired: Boolean = false,
    override val label: Label = Label(name, isRequired),
    override val hint: String = label.capitalizedWithAstrix(),
    override val isReadonly: Boolean = false,
    private val value: Boolean? = null,
    validator: ((Boolean?) -> Unit)? = null,
) : BooleanInputField {
    override val data = mutableLiveOf(OutputData(value))
    override val feedback: MutableLive<InputFieldState> = mutableLiveOf(InputFieldState.Empty)
    override val serializer: KSerializer<Boolean> = Boolean.serializer()

    private val bv = CompoundValidator1(
        feedback,
        RequirementValidator(feedback, label.capitalizedWithoutAstrix(), isRequired),
        LambdaValidator(feedback, validator)
    )

    private val setter = OutputSetter(data, feedback, bv)
    override fun set(value: Boolean) = setter.set(value)

    private val clearer = Clearer(OutputData(value), data, feedback)
    override fun clear() = clearer.clear()

    override fun validate(value: Boolean?) = bv.validate(value)
    override fun validate() = bv.validate(data.value.output)
    override fun validateSettingInvalidsAsErrors(value: Boolean?) = bv.validateSettingInvalidsAsErrors(value)
    override fun validateSettingInvalidsAsErrors() = bv.validateSettingInvalidsAsErrors(data.value.output)
    override fun validateSettingInvalidsAsWarnings(value: Boolean?) = bv.validateSettingInvalidsAsWarnings(value)
    override fun validateSettingInvalidsAsWarnings() = bv.validateSettingInvalidsAsWarnings(data.value.output)

    override fun toggle() = set(
        when (val value = data.value.output) {
            null -> true
            else -> !value
        }
    )
}