@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.fields

import presenters.validation.Validateable1
import kotlin.js.JsExport
import kotlin.js.JsName

interface TransformingValuedField<I, O> : TransformedData<I, O>, Validateable1<O> {
    val formatter: Formatter<O>?
    val transformer: (I?) -> O?

    @JsName("setValue")
    fun set(value: I?)
}