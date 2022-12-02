@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.confirmations

import koncurrent.Later
import live.Live
import presenters.actions.MutableSimpleAction
import presenters.confirmations.internal.ConfirmationBoxImpl
import presenters.states.LazyState
import viewmodel.ScopeConfig
import kotlin.js.JsExport

interface ConfirmationBox {
    val heading: String
    val details: String

    val state: Live<LazyState<Unit>>

    val cancelAction: MutableSimpleAction

    fun cancel(): Later<Any?>
    fun confirm(): Later<Any?>

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