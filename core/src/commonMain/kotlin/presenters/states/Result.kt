package presenters.states

import kotlin.js.JsExport

@JsExport
sealed interface Result<out D> {
    val data: D?

    val isSuccess: Boolean
    val isFailure: Boolean

    val asSuccess: Success<D>
    val asFailure: Failure<D>

    fun <R> map(transformer: (D) -> R): Result<R>
}