@file:Suppress("WRONG_EXPORTED_DECLARATION", "NON_EXPORTABLE_TYPE")

package presenters.states

import kotlin.js.JsExport

@JsExport
sealed interface Case {
    val isLoading: Boolean
    val isSuccess: Boolean
    val isFailure: Boolean

    val asLoading: Loading<Any?>
    val asSuccess: Success<Any?>
    val asFailure: Failure<Any?>
}
