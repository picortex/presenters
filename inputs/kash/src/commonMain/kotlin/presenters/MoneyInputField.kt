@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters

import kash.Currency
import kash.Money
import kotlin.js.JsExport
import kotlin.js.JsName

interface MoneyInputField : MonetaryValueInputField<Money> {
    val currency: SingleChoiceInputField<Currency>
    val amount: MonetaryInputField

    @JsName("setCurrencyValue")
    fun setCurrency(currency: Currency)

    fun setCurrency(value: String)
}