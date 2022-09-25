@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.actions

import koncurrent.Later
import presenters.actions.internal.MutableGenericActionImpl
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.jvm.JvmName

interface GenericAction<in T> : Action<(T) -> Later<Any?>> {
    operator fun invoke(arg: T): Later<Any?>

    companion object {
        fun <T> ofLater(
            name: String,
            handler: (T) -> Later<Any?>
        ): GenericAction<T> = MutableGenericActionImpl(name, handler)

        operator fun <T> invoke(
            name: String,
            handler: (T) -> Unit
        ): GenericAction<T> = MutableGenericActionImpl(name) { res ->
            Later.resolve(res).then { handler(it) }
        }
    }
}