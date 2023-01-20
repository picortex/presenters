@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters

import presenters.fields.properties.Clearable
import presenters.fields.properties.Hintable
import presenters.fields.properties.Labeled
import presenters.fields.properties.Mutability
import presenters.fields.properties.Requireble
import presenters.fields.properties.Settable
import presenters.fields.properties.Typeable
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