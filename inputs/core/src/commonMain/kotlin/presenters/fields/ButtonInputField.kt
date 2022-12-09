@file:JsExport

package presenters.fields

import kotlin.js.JsExport

data class ButtonInputField(
    override val name: String,
    override val label: InputLabel = InputLabel(name, isRequired = true)
) : InputField