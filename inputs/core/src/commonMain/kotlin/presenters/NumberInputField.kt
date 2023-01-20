@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters

import presenters.properties.Clearable
import presenters.properties.Hintable
import presenters.properties.Labeled
import presenters.properties.Mutability
import presenters.properties.Requireble
import presenters.properties.Settable
import presenters.properties.Typeable
import presenters.validation.Validateable0
import presenters.validation.Validateable1
import kotlin.js.JsExport
import kotlin.js.JsName

interface NumberInputField<N : Number> : InputField, Labeled, Hintable, Mutability, Requireble, Settable<String>, LiveFormattedData<String, N>, Validateable1<N>, Validateable0, Typeable, Clearable {
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