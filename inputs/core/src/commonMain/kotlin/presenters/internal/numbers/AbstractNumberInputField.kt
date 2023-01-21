package presenters.internal.numbers

import live.MutableLive
import live.mutableLiveOf
import presenters.Label
import presenters.NumberInputField
import presenters.Formatter
import presenters.InputFieldState
import presenters.internal.FormattedData
import presenters.internal.utils.Clearer
import presenters.internal.utils.DataTransformer
import presenters.internal.utils.FormattedOutputSetter
import presenters.internal.utils.Typer
import presenters.internal.validators.ClippingValidator
import presenters.internal.validators.CompoundValidator
import presenters.internal.validators.LambdaValidator
import presenters.internal.validators.RequirementValidator

@PublishedApi
internal abstract class AbstractNumberInputField<N>(
    final override val name: String,
    final override val isRequired: Boolean = false,
    final override val label: Label = Label(name, isRequired),
    final override val hint: String = label.text,
    final override val isReadonly: Boolean = false,
    final override val max: N? = null,
    final override val min: N? = null,
    final override val step: N? = null,
    private val formatter: Formatter<N>? = null,
    private val value: N? = null,
    validator: ((N?) -> Unit)? = null
) : NumberInputField<N> where N : Comparable<N>, N : Number {
    private val default = FormattedData<String, N>(null, "", value)
    final override val data = mutableLiveOf(default)
    final override val feedback: MutableLive<InputFieldState> = mutableLiveOf(InputFieldState.Empty)

    private val dv = CompoundValidator(
        data, feedback,
        RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        ClippingValidator(data, feedback, label.capitalizedWithoutAstrix(), max, min),
        LambdaValidator(data, feedback, validator)
    )

    abstract val transformer: DataTransformer<String, N>

    internal val setter by lazy { FormattedOutputSetter(data, feedback, transformer, dv) }

    override fun set(value: String) = setter.set(value)

    override fun type(text: String) = Typer(data.value.input, setter).type(text)

    private val clearer = Clearer(default, data, feedback)
    override fun clear() = clearer.clear()

    override fun set(double: Double) = setter.set(double.toString())

    override fun set(integer: Int) = setter.set(integer.toString())

    override fun validate(value: N?) = dv.validate(value)
    override fun validateSettingInvalidsAsErrors(value: N?) = dv.validateSettingInvalidsAsErrors(value)
    override fun validateSettingInvalidsAsWarnings(value: N?) = dv.validateSettingInvalidsAsWarnings(value)
}