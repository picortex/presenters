@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters

import kash.Currency
import kash.Money
import presenters.properties.Typeable
import kotlin.js.JsExport
import kotlin.js.JsName

interface MoneyInputField : TransformingInputField<String, Money>, Typeable {
    val currency: SingleChoiceInputField<Currency>
    val amount: NumberInputField<Double>

    @JsName("setCurrencyValue")
    fun setCurrency(currency: Currency)

    fun setCurrency(value: String)

    @JsName("setAmountString")
    fun setAmount(value: String)

    fun setAmount(number: Double)

    @JsName("setAmountInt")
    fun setAmount(number: Int)
}