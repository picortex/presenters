@file:Suppress("WRONG_EXPORTED_DECLARATION", "NON_EXPORTABLE_TYPE")

package presenters.actions

import koncurrent.Later
import presenters.actions.internal.MutableSimpleActionImpl
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.jvm.JvmName

@JsExport
interface SimpleAction : Action<() -> Later<Any?>> {
    operator fun invoke(): Later<Any?>

    companion object {
        fun ofLater(
            name: String,
            handler: () -> Later<Any?>
        ): SimpleAction = MutableSimpleActionImpl(name, handler)

        operator fun invoke(
            name: String,
            handler: () -> Unit
        ): SimpleAction = MutableSimpleActionImpl(name) {
            Later(Unit).then { handler() }
        }
    }
}