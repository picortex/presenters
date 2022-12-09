@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.fields

import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
interface TextBasedValuedField<T> : ValuedField<T> {
    val defaultText: String?

    /**
     * Transforms this text into an object of type [T]
     */
    val transformer: (String?) -> T?

    /**
     * Returns the value [T] or if the text is empty
     */
    val valueOrNull: T?

    @JsName("setText")
    fun set(text: String)

    fun type(text: String) {
        for (i in 0..text.lastIndex) set(text.substring(0..i))
    }
}