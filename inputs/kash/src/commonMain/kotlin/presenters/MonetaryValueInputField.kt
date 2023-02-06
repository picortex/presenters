@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters

import kash.MonetaryValue
import presenters.properties.Typeable
import kotlin.js.JsExport
import kotlin.js.JsName

interface MonetaryValueInputField<V : MonetaryValue> : TransformingInputField<String, V>, Typeable {
    @JsName("setAmountString")
    fun setAmount(value: String?)

    fun setAmount(number: Double?)

    @JsName("setAmountInt")
    fun setAmount(number: Int?)
}