@file:JsExport

package presenters.fields

import kotlin.js.JsExport

data class InputData<out I>(
    val raw: I,
    val formatted: I
)