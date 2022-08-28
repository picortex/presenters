package presenters.fields

import kotlin.js.JsExport

@JsExport
data class Option(
    val label: String,
    val value: String = label,
    val selected: Boolean = false
)