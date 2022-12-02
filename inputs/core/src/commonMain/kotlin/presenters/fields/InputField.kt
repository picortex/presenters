package presenters.fields

import kotlin.js.JsExport

@JsExport
interface InputField {
    val name: String
    val label: String
}