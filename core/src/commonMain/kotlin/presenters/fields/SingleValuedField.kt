@file:Suppress("WRONG_EXPORTED_DECLARATION")

package presenters.fields

import kotlin.js.JsExport

@JsExport
interface SingleValuedField<T : Any> : ValuedField {
    val defaultValue: T?
    val value: T?
    val validator: ((T?) -> Unit)?
    fun validate(value: T? = this.value)
    fun validateWithFeedback(value: T? = this.value)

    companion object {
        val DEFAULT_VALUE: Nothing? = null
    }
}