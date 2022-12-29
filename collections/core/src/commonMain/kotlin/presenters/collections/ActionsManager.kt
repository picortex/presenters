@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import actions.Action0I1R
import kollections.List
import live.Live
import kotlin.js.JsExport

@JsExport
interface ActionsManager<in T> {
    val actions: Live<List<Action0I1R<Unit>>>

    fun actionsOf(item: T): List<Action0I1R<Unit>>
}