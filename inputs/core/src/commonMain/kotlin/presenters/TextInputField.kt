@file:JsExport

package presenters

import kotlin.js.JsExport

interface TextInputField : BasicTextInputField {
    val maxLength: Int?
    val minLength: Int?
}