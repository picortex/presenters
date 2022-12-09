@file:JsExport

package presenters.fields

import kotlin.js.JsExport

interface InputField {
    val name: String
    val label: InputLabel
}