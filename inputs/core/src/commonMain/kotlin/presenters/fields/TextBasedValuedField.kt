@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.fields

import kotlin.js.JsExport

@JsExport
interface TextBasedValuedField<out O : Any> : SingleValuedField<String, O> {
    fun type(text: String) {
        for (i in 0..text.lastIndex) set(text.substring(0..i))
    }
}