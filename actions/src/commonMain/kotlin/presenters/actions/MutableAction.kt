package presenters.actions

import kotlin.js.JsExport

@JsExport
interface MutableAction<H> : Action<H> {
    override var handler: H
}