package presenters.internal

import kotlinx.serialization.builtins.serializer
import live.Live
import live.MutableLive
import live.mutableLiveOf
import presenters.Label
import presenters.TextInputField
import presenters.fields.InputFieldState
import presenters.fields.internal.OutputData
import presenters.validation.Invalid
import presenters.validation.Valid

@PublishedApi
internal class TextInputFieldImpl(
    override val name: String,
    override val isRequired: Boolean = false,
    override val label: Label = Label(name, isRequired),
    override val hint: String = label.capitalizedWithAstrix(),
    override val isReadonly: Boolean = false,
    override val maxLength: Int? = null,
    override val minLength: Int? = null,
    private val value: String? = null,
    validator: ((String?) -> Unit)? = null,
) : TextInputField {
    override val data = mutableLiveOf(OutputData(value))
    override val serializer = String.serializer()
    override val feedback: MutableLive<InputFieldState> = mutableLiveOf(InputFieldState.Empty)

    private val tv = CompoundValidator(
        feedback,
        RequirementValidator(feedback, label.capitalizedWithoutAstrix(), isRequired),
        TextValidator(feedback, label.capitalizedWithoutAstrix(), isRequired, maxLength, minLength),
        LambdaValidator(feedback, validator)
    )

    override fun validate(value: String?) = tv.validate(value)
    override fun validate() = tv.validate(data.value.output)
    override fun validateSettingInvalidsAsErrors(value: String?) = tv.validateSettingInvalidsAsErrors(value)
    override fun validateSettingInvalidsAsErrors() = tv.validateSettingInvalidsAsErrors(data.value.output)
    override fun validateSettingInvalidsAsWarnings(value: String?) = tv.validateSettingInvalidsAsWarnings(value)
    override fun validateSettingInvalidsAsWarnings() = tv.validateSettingInvalidsAsWarnings(data.value.output)

    private val setter = OutputSetter(data, feedback, tv)

    override fun set(value: String) = setter.set(value)

    private val clearer = OutputClearer(value,data,feedback)

    override fun clear() = clearer.clear()

    override fun type(text: String) {
        val old = data.value.output ?: ""
        for (i in 0..text.lastIndex) set(old + text.substring(0..i))
    }
}