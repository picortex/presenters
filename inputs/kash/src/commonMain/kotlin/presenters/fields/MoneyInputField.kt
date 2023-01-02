@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import kash.Currency
import kash.CurrencySerializer
import kash.Money
import kollections.toIList
import kotlinx.serialization.KSerializer
import live.mutableLiveOf
import live.watch
import presenters.fields.internal.AbstractValuedField
import presenters.validation.ValidationResult
import kotlin.js.JsExport

class MoneyInputField(
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
) : AbstractValuedField<String, Money>(name, isRequired, label, null, { error("") }, isReadonly, null) {

    val currency: SingleChoiceValuedField<Currency> = SingleChoiceValuedField(
        name = "$name-currency",
        isRequired = isRequired,
        label = InputLabel("$name currency", isRequired),
        isReadonly = !selectCurrency,
        items = Currency.values.toIList(),
        mapper = { Option(it.name, it.name) },
        serializer = CurrencySerializer,
        defaultValue = currency
    )

    val amount: NumberBasedValuedField<Double> = NumberBasedValuedField(
        name = "$name-amount",
        isRequired = isRequired,
        label = InputLabel("$name currency", isRequired),
        hint = hint,
        defaultValue = defaultValue?.amountAsDouble?.toString(),
        isReadonly = isReadonly,
        max = max,
        min = min,
        step = step,
        validator = null
    )

    override val data = mutableLiveOf<Money?>(null)

    init {
        watch(this.currency.data, amount.data) { curncy, amnt ->
            val a = amnt ?: return@watch
            val c = curncy ?: return@watch
            data.value = Money.of(a, c)
        }
    }

    override fun validate(value: String?): ValidationResult = amount.validate(value)

    override val serializer: KSerializer<Money> = Money.serializer()
}