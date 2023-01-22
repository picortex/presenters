package presenters.internal.text

import kotlinx.serialization.builtins.serializer
import live.MutableLive
import live.mutableLiveOf
import presenters.InputFieldState
import presenters.Label
import presenters.TextInputField
import presenters.internal.OutputData
import presenters.internal.utils.Clearer
import presenters.internal.utils.OutputSetter
import presenters.internal.utils.Typer
import presenters.internal.validators.CompoundValidator

abstract class AbstractBasicTextInputField(
    final override val name: String,
    final override val isRequired: Boolean = false,
    final override val label: Label = Label(name, isRequired),
    final override val hint: String = label.capitalizedWithAstrix(),
    final override val isReadonly: Boolean = false,
    final override val maxLength: Int? = null,
    final override val minLength: Int? = null,
    private val value: String? = null
) : TextInputField {
    private val default = OutputData(value)
    final override val data = mutableLiveOf(default)
    final override val serializer = String.serializer()
    final override val feedback: MutableLive<InputFieldState> = mutableLiveOf(InputFieldState.Empty)

    abstract val tv: CompoundValidator<String>

    final override fun validate(value: String?) = tv.validate(value)
    final override fun validateSettingInvalidsAsErrors(value: String?) = tv.validateSettingInvalidsAsErrors(value)
    final override fun validateSettingInvalidsAsWarnings(value: String?) = tv.validateSettingInvalidsAsWarnings(value)

    private val setter by lazy { OutputSetter(data, feedback, tv) }

    final override fun set(value: String) = setter.set(value)

    private val clearer = Clearer(default, data, feedback)

    final override fun clear() = clearer.clear()

    final override fun type(text: String) = Typer(data.value.output ?: "", setter).type(text)
}