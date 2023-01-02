@file:JsExport

package presenters.validation

import kotlin.js.JsExport

data class Invalid(val cause: Throwable) : ValidationResult