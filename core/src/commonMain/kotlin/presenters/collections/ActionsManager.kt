@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import kotlinx.collections.interoperable.List
import presenters.actions.SimpleAction
import kotlin.js.JsExport

@JsExport
interface ActionsManager<in T> {
    val actions: List<SimpleAction>

    fun actionsOf(item: T): List<SimpleAction>
}