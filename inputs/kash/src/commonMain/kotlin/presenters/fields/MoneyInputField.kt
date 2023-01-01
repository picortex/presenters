@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import kash.Currency
import kash.CurrencySerializer
import kash.Money
import kollections.toIList
import presenters.fields.internal.DoubleInputField
import presenters.fields.internal.NumberBasedValueField
import kotlin.js.JsExport

class MoneyInputField(
    override val name: String,
    override val isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    hint: String = label.text,
    defaultValue: Money? = SingleValuedField.DEFAULT_VALUE,
    val currency: Currency? = defaultValue?.currency,
    val selectCurrency: Boolean = true,
    override val isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    max: Double? = NumberBasedValueField.DEFAULT_MAX,
    min: Double? = NumberBasedValueField.DEFAULT_MIN,
    step: Double? = DoubleInputField.DEFAULT_STEP,
) : ValuedField<Money> {

    val currencies: SingleChoiceValuedField<Currency> = SingleChoiceValuedField(
        name = "$name-currency",
        isRequired = isRequired,
        label = InputLabel("$name currency", isRequired),
        isReadonly = !selectCurrency,
        items = Currency.values.toIList(),
        mapper = { Option(it.name, it.name) },
        serializer = CurrencySerializer,
        defaultValue = currency
    )

    val amount: NumberBasedValueField<Double> = DoubleInputField(
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
}