@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import kotlin.js.JsExport

interface InputField {
    val name: String
    val label: InputLabel
}