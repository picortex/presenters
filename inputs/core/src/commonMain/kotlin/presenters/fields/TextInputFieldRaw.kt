package presenters.fields

import kotlin.js.JsExport

@JsExport
interface TextInputFieldRaw : InputFieldWithValue<String?> {
    val hint: String
}