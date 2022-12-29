@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.actions

import koncurrent.Later
import presenters.actions.internal.MutableGenericActionImpl
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.jvm.JvmName

interface MutableGenericAction<T> : GenericAction<T>, MutableAction<(T) -> Later<Any?>> {
    override var handler: (T) -> Later<Any?>

    fun onInvoked(h: (T) -> Unit)

    companion object {
        fun <T> ofLater(
            name: String,
            handler: (T) -> Later<Any?>
        ): MutableGenericAction<T> = MutableGenericActionImpl(name, handler)

        operator fun <T> invoke(
            name: String,
            handler: (T) -> Any?
        ): MutableGenericAction<T> = MutableGenericActionImpl(name) { res ->
            Later(res).then { handler(it) }
        }
    }
}