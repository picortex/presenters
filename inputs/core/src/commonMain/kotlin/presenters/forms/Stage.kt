@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.forms

import kotlin.js.JsExport

data class Stage<out F : Fields>(
    val name: String,
    val fields: F
)