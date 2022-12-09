@file:JsExport

package presenters.fields

import kotlin.js.JsExport

data class InputLabel(
    val text: String,
    internal val isRequired: Boolean = false
) {
    fun capitalized(withAstrix: Boolean) = text.lowercase().replaceFirstChar { it.uppercase() } + if (isRequired && withAstrix) "*" else ""

    fun capitalizedWithAstrix() = capitalized(withAstrix = true)

    fun capitalizedWithoutAstrix() = capitalized(withAstrix = false)
}