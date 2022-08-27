@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.states

import kotlin.js.JsExport

@JsExport
sealed interface LazyState<out T> {
    val data: T?

    val isPending: Boolean
    val isLoading: Boolean
    val isSuccess: Boolean
    val isFailure: Boolean

    val asLoading: Loading<T>
    val asSuccess: Success<T>
    val asFailure: Failure<T>

    fun <R> map(transformer: (T) -> R): LazyState<R>
}