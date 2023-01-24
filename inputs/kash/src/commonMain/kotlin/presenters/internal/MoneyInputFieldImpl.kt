package presenters.internal

import kash.Currency
import kash.CurrencySerializer
import kash.Money
import kollections.toIList
import kotlinx.serialization.KSerializer
import live.MutableLive
import live.mutableLiveOf
import live.watch
import presenters.DoubleInputField
import presenters.Formatter
import presenters.Label
import presenters.MoneyInputField
import presenters.Option
import presenters.InputFieldState
import presenters.LongInputField
import presenters.SingleChoiceInputField
import presenters.internal.utils.Clearer
import presenters.internal.utils.DataTransformer
import presenters.internal.utils.Typer
import presenters.internal.validators.ClippingValidator
import presenters.internal.validators.CompoundValidator
import presenters.internal.validators.LambdaValidator
import presenters.internal.validators.RequirementValidator
import presenters.validation.Invalid
import presenters.validation.ValidationResult

@PublishedApi
internal class MoneyInputFieldImpl(
    override val name: String,
    override val isRequired: Boolean,
    override val label: Label,
    override val hint: String,
    private val value: Money?,
    private val fixedCurrency: Currency?,
    private val selectCurrency: Boolean,
    override val isReadonly: Boolean,
    private val formatter: Formatter<Double>?,
    private val maxAmount: Double?,
    private val minAmount: Double?,
    private val stepAmount: Double?,
    private val validator: ((Money?) -> Unit)?
) : TransformedDataField<String, Money>(value), MoneyInputField {

    override val transformer: DataTransformer<String, Money> get() = error("Don't transform directly")

    private val theCurrency = fixedCurrency ?: value?.currency

    override val currency = SingleChoiceInputField(
        name = "$name-currency",
        isRequired = isRequired,
        label = "$name currency",
        isReadonly = !selectCurrency,
        items = Currency.values.toIList(),
        mapper = { Option(it.name, it.name) },
        serializer = CurrencySerializer,
        value = theCurrency
    )

    override val amount = DoubleInputField(
        name = "$name-amount",
        isRequired = isRequired,
        label = "$name amount",
        hint = hint,
        value = value?.amountAsDouble,
        isReadonly = isReadonly,
        formatter = formatter,
        max = maxAmount,
        min = minAmount,
        step = stepAmount,
        validator = null
    )

    private fun money(amount: Double?): Money? {
        val cur = theCurrency ?: return null
        val a = amount ?: return null
        return Money.of(a, cur)
    }

    override val cv = CompoundValidator(
        data, feedback,
        RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        ClippingValidator(data, feedback, label.capitalizedWithoutAstrix(), money(maxAmount), money(minAmount)),
        LambdaValidator(data, feedback, validator)
    )

    init {
        watch(this.currency.data, this.amount.data) { cur, amm ->
            val c = cur.output ?: return@watch
            val a = amm.output ?: return@watch
            data.value = FormattedData("${c.name} $a", "${c.name} $a", Money.of(a, c))
        }
    }

    override fun validate(value: Money?): ValidationResult {
        arrayOf(
            currency.validate(value?.currency),
            amount.validate(value?.amountAsDouble)
        ).forEach { res ->
            if (res is Invalid) return res
        }
        return super.validate(value)
    }


    override fun type(text: String) = Typer(default.input, amount).type(text)

    override fun set(value: String?) = amount.set(value)

    override fun setAmount(number: Int) = amount.set(number)

    override val serializer: KSerializer<Money> = Money.serializer()

    override fun clear() {
        currency.clear()
        amount.clear()
        super.clear()
    }

    override fun setAmount(value: String) = amount.set(value)

    override fun setCurrency(value: String) = currency.selectValue(value)

    override fun setCurrency(currency: Currency) = this.currency.selectItem(currency)

    override fun setAmount(number: Double) = amount.set(number)
}