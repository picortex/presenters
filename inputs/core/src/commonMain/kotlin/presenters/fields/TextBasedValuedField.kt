@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.fields

import kotlin.js.JsExport

interface TextBasedValuedField<out O : Any> : SingleValuedField<String, O> {
    val hint: String
    fun type(text: String)
}