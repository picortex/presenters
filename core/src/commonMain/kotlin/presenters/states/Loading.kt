@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.states

import kotlin.js.JsExport

@JsExport
data class Loading<out D>(
    val message: String,
    override val data: D? = null,
) : LazyState<D>, MissionState<D>, Case, Feedback {
    override val isPending = false
    override val isLoading = true
    override val isSuccess = false
    override val isFailure = false

    override val asLoading: Loading<D> get() = this
    override val asSuccess: Success<D> get() = error(0)
    override val asFailure: Failure<D> get() = error(0)

    override fun <R> map(transformer: (D) -> R): LazyState<R> = try {
        if (data != null) {
            Loading(message, transformer(data))
        } else {
            Loading(message, null)
        }
    } catch (err: Throwable) {
        Failure(err)
    }
}