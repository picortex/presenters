@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.confirmations

import actions.MutableAction0
import kase.ExecutorState
import koncurrent.Thenable
import live.Live
import presenters.confirmations.internal.ConfirmationBoxImpl
import viewmodel.ScopeConfig
import kotlin.js.JsExport

interface ConfirmationBox {
    val heading: String
    val details: String

    val state: Live<ExecutorState<Unit>>

    val cancelAction: MutableAction0<Unit>

    fun cancel(): Thenable<Any?>
    fun confirm(): Thenable<Any?>
}