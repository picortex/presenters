@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.confirmations

import actions.MutableAction0I1R
import kase.ExecutorState
import koncurrent.Later
import koncurrent.Thenable
import live.Live
import presenters.confirmations.internal.ConfirmationBoxImpl
import viewmodel.ScopeConfig
import kotlin.js.JsExport

interface ConfirmationBox {
    val heading: String
    val details: String

    val state: Live<ExecutorState<Unit>>

    val cancelAction: MutableAction0I1R<Unit>

    fun cancel(): Thenable<Any?>
    fun confirm(): Thenable<Any?>

    companion object {
        const val DEFAULT_EXECUTION_MESSAGE: String = "Executing, please wait . . ."
        operator fun invoke(
            heading: String,
            details: String,
            message: String = DEFAULT_EXECUTION_MESSAGE,
            config: ScopeConfig<*>,
            actionsBuilder: ConfirmActionsBuilder.() -> Unit
        ): ConfirmationBox = ConfirmationBoxImpl(heading, details, message, config, actionsBuilder)
    }
}