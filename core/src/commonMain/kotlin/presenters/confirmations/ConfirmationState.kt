@file:JsExport

package presenters.confirmations

import kotlin.js.JsExport

// Make it use lazy state
sealed class ConfirmationState {
    object Pending : ConfirmationState()
    object Executing : ConfirmationState()
    sealed class Executed : ConfirmationState() {
        object Successfully : ConfirmationState()
        object Exceptionally : ConfirmationState()
    }

    override fun toString() = this::class.simpleName ?: "Unknown"

    val isPending get() = this is Pending
    val isExecuting get() = this is Executing
    val isExecuted get() = this is Executed

    val executedSuccessfully get() = this is Executed.Successfully
    val executedExceptionally get() = this is Executed.Exceptionally
}
