@file:JsExport

package presenters.actions

import kotlinx.collections.interoperable.mutableListOf
import kotlin.js.JsExport

open class SimpleActionsBuilder : ActionsBuilder<SimpleAction, () -> Unit> {
    val actions = mutableListOf<SimpleAction>()
    override fun on(name: String, handler: () -> Unit): SimpleAction {
        val action = SimpleAction(name, handler)
        actions.add(action)
        return action
    }
}