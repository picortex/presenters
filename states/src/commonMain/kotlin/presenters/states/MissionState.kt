@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.states

import kotlin.js.JsExport

sealed interface MissionState<out T> {
    val data: T?

    val isLoading: Boolean
    val isSuccess: Boolean
    val isFailure: Boolean

    val asLoading: Loading<T>
    val asSuccess: Success<T>
    val asFailure: Failure<T>
}