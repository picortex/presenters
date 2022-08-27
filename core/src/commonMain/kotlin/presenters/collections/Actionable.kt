package presenters.collections

import kotlin.js.JsExport

@JsExport
interface Actionable<in T> {
    val actionsManager: ActionsManager<T>
}