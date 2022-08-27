@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.confirmations

import koncurrent.Later
import live.Live
import presenters.actions.MutableSimpleAction
import presenters.confirmations.internal.ConfirmationBoxImpl
import presenters.forms.FormActionsBuilder
import viewmodel.ScopeConfig
import kotlin.js.JsExport

interface ConfirmationBox {
    val heading: String
    val details: String

    val ui: Live<ConfirmationState>

    val cancelAction: MutableSimpleAction

    fun confirm(): Later<Any?>

    companion object {
        operator fun invoke(
            heading: String,
            details: String,
            config: ScopeConfig<*>,
            actionsBuilder: ConfirmActionsBuilder.() -> Unit
        ): ConfirmationBox = ConfirmationBoxImpl(heading, details, config, actionsBuilder)
    }
}