@file:JsExport
@file:Suppress("WRONG_EXPORTED_DECLARATION")

package presenters.states

import kotlin.js.JsExport

sealed interface Feedback {
    val isNone get() = this is None
}