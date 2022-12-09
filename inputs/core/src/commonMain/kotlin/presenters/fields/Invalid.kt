@file:JsExport

package presenters.fields

import kotlin.js.JsExport

data class Invalid(val cause: Throwable) : ValidationResult