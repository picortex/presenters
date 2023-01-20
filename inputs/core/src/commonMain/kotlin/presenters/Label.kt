@file:JsExport

package presenters

import kotlin.js.JsExport

data class Label(
    val text: String,
    internal val isRequired: Boolean = false
) {
    private fun capitalized(withAstrix: Boolean) = text.lowercase().replaceFirstChar { it.uppercase() } + if (isRequired && withAstrix) "*" else ""

    fun capitalizedWithAstrix() = capitalized(withAstrix = true)

    fun capitalizedWithoutAstrix() = capitalized(withAstrix = false)
}