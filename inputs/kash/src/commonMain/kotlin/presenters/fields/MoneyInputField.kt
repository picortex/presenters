@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import kash.Currency
import kash.Money
import live.Live
import presenters.validation.Validateable0
import kotlin.js.JsExport
import kotlin.js.JsName

interface MoneyInputField : ValuedField<Money>, Validateable0 {
    override val data: Live<FormattedData<String, Money>>
    val currency: SingleChoiceValuedField<Currency>
    val amount: TextBasedValuedField<Double>

    @JsName("setCurrencyValue")
    fun setCurrency(currency: Currency)

    fun setCurrency(value: String)

    @JsName("setAmountString")
    fun setAmount(value: String)

    fun setAmount(number: Double)

    @JsName("setAmountInt")
    fun setAmount(number: Number)
}