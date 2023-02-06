package presenters.internal.numbers

import formatter.NumberFormatter
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
    isRequired: Boolean,
    label: Label,
    hint: String,
    isReadonly: Boolean,
    max: N?,
    min: N?,
    step: N?,
    formatter: NumberFormatter?,
    value: N?,
    validator: ((N?) -> Unit)?
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