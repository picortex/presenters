package presenters.actions

import kotlin.js.JsExport

@JsExport
interface Action<out H> {
    val name: String
    val handler: H
}