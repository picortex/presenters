@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.states

import kotlin.js.JsExport

@JsExport
sealed interface LazyState<out T> : MissionState<T> {
    val isPending: Boolean
}