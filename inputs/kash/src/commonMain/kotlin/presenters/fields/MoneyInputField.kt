@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import kash.Currency
import kash.Money
import presenters.validation.Validateable0
import kotlin.js.JsExport
import kotlin.js.JsName

interface MoneyInputField : ValuedField<Money>, Validateable0 {
    val currency: SingleChoiceValuedField<Currency>
    val amount: TextBasedValuedField<Double>

    fun setCurrency(value: String)

    @JsName("setAmountString")
    fun setAmount(value: String)

    @JsName("setAmount")
    fun setAmount(number: Double)

    @JsName("setAmountInt")
    fun setAmount(number: Number)
}