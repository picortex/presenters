package presenters.internal

import formatter.NumberFormatter
import kash.Monetary
import kash.serializers.MonetarySerializer
import kotlinx.serialization.KSerializer
import presenters.Formatter
import presenters.Label
import presenters.MonetaryInputField
import presenters.internal.utils.DataTransformer
import presenters.internal.utils.Typer
import presenters.internal.validators.ClippingValidator
import presenters.internal.validators.CompoundValidator
import presenters.internal.validators.LambdaValidator
import presenters.internal.validators.RequirementValidator

@PublishedApi
internal class MonetaryInputFieldImpl(
    override val name: String,
    override val isRequired: Boolean,
    override val label: Label,
    override val hint: String,
    private val value: Monetary?,
    override val isReadonly: Boolean,
    private val formatter: NumberFormatter?,
    private val maxAmount: Monetary?,
    private val minAmount: Monetary?,
    private val stepAmount: Double?,
    private val validator: ((Monetary?) -> Unit)?
) : TransformedDataField<String, Monetary>(value), MonetaryInputField {

    override val cv = CompoundValidator(
        data, feedback,
        RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        ClippingValidator(data, feedback, label.capitalizedWithoutAstrix(), maxAmount, minAmount),
        LambdaValidator(data, feedback, validator)
    )

    override val transformer = DataTransformer(
        formatter = Formatter { long ->
            val l = long ?: return@Formatter null
            val fmt = formatter ?: return@Formatter null
            fmt.format(l.amountAsDouble)
        },
        transformer = { amount: String? ->
            amount?.replace(",", "")?.toDoubleOrNull()?.let { Monetary(it) }
        }
    )

    override fun type(text: String) = Typer(data.value.input, setter).type(text)

    override fun set(value: String?) = setter.set(value)

    override fun setAmount(number: Int?) = setter.set(number?.toString())

    override val serializer: KSerializer<Monetary> = MonetarySerializer

    override fun setAmount(value: String?) = setter.set(value)

    override fun setAmount(number: Double?) = setter.set(number?.toString())
}