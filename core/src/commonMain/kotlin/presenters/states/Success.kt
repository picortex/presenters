@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.states

import kotlin.js.JsExport

@JsExport
data class Success<out D>(
    override val data: D
) : LazyState<D>, MissionState<D>, Result<D>, Case, Feedback {
    // TODO: Ensure you don't need actions on success
    // val actions: List<SimpleAction> = emptyList()
    override val isPending = false
    override val isLoading = false
    override val isSuccess = true
    override val isFailure = false

    override val asLoading: Loading<D> get() = error(0)
    override val asSuccess: Success<D> get() = this
    override val asFailure: Failure<D> get() = error(0)

    override fun <R> map(transformer: (D) -> R) = Success(transformer(data))
    override fun catch(resolver: (Throwable) -> @UnsafeVariance D): Result<D> = this
}