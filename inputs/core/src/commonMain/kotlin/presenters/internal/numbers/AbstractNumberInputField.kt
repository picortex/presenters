package presenters.internal.numbers

import presenters.Formatter
import presenters.Label
import presenters.NumberInputField
import presenters.internal.TransformedDataField
import presenters.internal.utils.Typer
import presenters.internal.validators.ClippingValidator
import presenters.internal.validators.CompoundValidator
import presenters.internal.validators.LambdaValidator
import presenters.internal.validators.RequirementValidator

@PublishedApi
internal abstract class AbstractNumberInputField<N>(
    name: String,
    isRequired: Boolean = false,
    label: Label = Label(name, isRequired),
    hint: String = label.text,
    isReadonly: Boolean = false,
    max: N? = null,
    min: N? = null,
    step: N? = null,
    formatter: Formatter<N>? = null,
    value: N? = null,
    validator: ((N?) -> Unit)? = null
) : TransformedDataField<String, N>(value), NumberInputField<N> where N : Comparable<N>, N : Number {
    override val cv: CompoundValidator<N> by lazy {
        CompoundValidator(
            data, feedback,
            RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
            ClippingValidator(data, feedback, label.capitalizedWithoutAstrix(), max, min),
            LambdaValidator(data, feedback, validator)
        )
    }

    override fun type(text: String) = Typer(data.value.input, setter).type(text)

    override fun set(double: Double) = setter.set(double.toString())

    override fun set(integer: Int) = setter.set(integer.toString())
}