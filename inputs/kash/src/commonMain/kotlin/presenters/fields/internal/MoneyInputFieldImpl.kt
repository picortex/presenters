package presenters.fields.internal

import kash.Currency
import kash.CurrencySerializer
import kash.Money
import kollections.toIList
import kotlinx.serialization.KSerializer
import live.mutableLiveOf
import live.watch
import presenters.fields.InputLabel
import presenters.fields.MoneyInputField
import presenters.fields.NumberBasedValuedField
import presenters.fields.DoubleValuedField
import presenters.fields.FormattedData
import presenters.fields.MoneyInputFormatter
import presenters.fields.Option
import presenters.fields.RawData
import presenters.fields.SingleChoiceValuedField
import presenters.fields.SingleValuedField
import presenters.validation.Invalid
import presenters.validation.ValidationResult

@PublishedApi
internal class MoneyInputFieldImpl(
    name: String,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    label: InputLabel = InputLabel(name, isRequired),
    hint: String = label.text,
    defaultValue: Money? = SingleValuedField.DEFAULT_VALUE,
    currency: Currency? = defaultValue?.currency,
    selectCurrency: Boolean = true,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    max: Double? = null,
    min: Double? = null,
    step: Double? = null,
    validator: ((String?) -> Unit)? = null
) : AbstractValuedField<String, Money>(name, isRequired, label, defaultValue?.toFormattedString(), isReadonly, validator), MoneyInputField {

    override val data = mutableLiveOf<FormattedData<String, Money>>(FormattedData(null, "", defaultValue))

    override val currency: SingleChoiceValuedField<Currency> = SingleChoiceValuedField(
        name = "$name-currency",
        isRequired = isRequired,
        label = InputLabel("$name currency", isRequired),
        isReadonly = !selectCurrency,
        items = Currency.values.toIList(),
        mapper = { Option(it.name, it.name) },
        serializer = CurrencySerializer,
        defaultValue = currency
    )

    override val amount: NumberBasedValuedField<Double> = DoubleValuedField(
        name = "$name-amount",
        isRequired = isRequired,
        label = InputLabel("$name currency", isRequired),
        hint = hint,
        defaultValue = defaultValue?.amountAsDouble?.toString(),
        isReadonly = isReadonly,
        formatter = MoneyInputFormatter,
        max = max,
        min = min,
        step = step,
        validator = null
    )

    init {
        watch(this.currency.data, this.amount.data) { cur, amm ->
            val c = cur.output ?: return@watch
            val a = amm.output ?: return@watch
            data.value = FormattedData("${c.name} $a", "${c.name} $a", Money.of(a, c))
        }
    }

    override fun validate(value: String?): ValidationResult {
        val curr = currency.validate()
        if (curr is Invalid) return curr

        return amount.validate(value)
    }

    override fun validate() = validate(data.value.raw)

    override fun set(value: String?) {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<Money> = Money.serializer()

    override fun clear() {
        amount.clear()
        data.value = FormattedData(null, "", null)
    }

    override fun validateSettingInvalidsAsErrors(): ValidationResult {
        TODO("Not yet implemented")
    }

    override fun validateSettingInvalidsAsWarnings(): ValidationResult {
        TODO("Not yet implemented")
    }

    override fun setAmount(value: String) = amount.set(value)

    override fun setCurrency(value: String) = currency.selectValue(value)

    override fun setAmount(number: Double) = amount.set(number.toString())

    override fun setAmount(number: Number) = amount.set(number.toString())

    private companion object {
        fun toDoubleForgivingErrors(it: String?): Double? {
            val str = it ?: return null
            return str.replace(",", "").toDoubleOrNull()
        }
    }
}