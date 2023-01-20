@file:JsExport

package presenters

import kotlin.js.JsExport

data class Option(
    val label: String,
    val value: String = label,
    val selected: Boolean = false
)