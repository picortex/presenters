@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.fields

import presenters.validation.Validateable
import kotlin.js.JsExport
import kotlin.js.JsName

@Deprecated("In favour of TransformingValuedField")
interface SingleValuedField<I, O> : ValuedField<O>, Validateable<I> {
    val defaultValue: I?

    @JsName("setValue")
    fun set(value: I?)

    companion object {
        val DEFAULT_IS_READONLY = false
        val DEFAULT_IS_REQUIRED = false
        val DEFAULT_VALIDATOR: Nothing? = null
        val DEFAULT_VALUE: Nothing? = null
    }
}