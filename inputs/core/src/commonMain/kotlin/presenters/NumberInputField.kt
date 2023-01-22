@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters

import presenters.properties.Settable
import presenters.properties.Typeable
import presenters.validation.Validateable
import kotlin.js.JsExport
import kotlin.js.JsName

interface NumberInputField<N : Number> : InputField, CommonInputProperties, Settable<String>, SerializableLiveFormattedData<String, N>, Validateable<N>, Typeable {
    val max: N?
    val min: N?
    val step: N?

    fun increment(step: N?)

    fun decrement(step: N?)

    @JsName("setDouble")
    fun set(double: Double)

    @JsName("setInt")
    fun set(integer: Int)
}