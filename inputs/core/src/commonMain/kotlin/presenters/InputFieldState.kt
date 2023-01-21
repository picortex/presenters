@file:JsExport

package presenters

import kotlin.js.JsExport

sealed class InputFieldState {
    object Empty : InputFieldState() {
        override fun toString() = "Empty"
    }

    data class Warning(val message: String, val cause: Throwable) : InputFieldState()
    data class Error(val message: String, val cause: Throwable) : InputFieldState()

    val asEmpty get() = this as? Empty
    val asWarning get() = this as? Warning
    val asError get() = this as? Error
}