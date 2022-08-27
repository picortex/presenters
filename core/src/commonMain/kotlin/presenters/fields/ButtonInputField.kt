package presenters.fields

import kotlin.js.JsExport

@JsExport
data class ButtonInputField(
    override val label: String,
    override val name: String = label
) : InputField