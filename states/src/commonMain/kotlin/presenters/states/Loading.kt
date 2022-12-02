@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.states

import kotlin.js.JsExport

data class Loading<out D>(
    val message: String,
    override val data: D? = null,
) : LazyState<D>, MissionState<D>, Case, Feedback {
    override val isPending = false
    override val isLoading = true
    override val isSuccess = false
    override val isFailure = false

    override val asLoading: Loading<D> get() = this
    override val asSuccess: Success<D> get() = error("a Loading state can't be casted to Success state")
    override val asFailure: Failure<D> get() = error("a Loading state can't be casted to Failure state")

    override fun <R> map(transformer: (D) -> R): LazyState<R> {
        if (data == null) return Loading(message, null)

        return try {
            Loading(message, transformer(data))
        } catch (err: Throwable) {
            Failure(err)
        }
    }
}