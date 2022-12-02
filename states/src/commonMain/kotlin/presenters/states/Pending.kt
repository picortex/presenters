@file:JsExport

package presenters.states

import kotlin.js.JsExport

object Pending : LazyState<Nothing> {
    override val isPending = true
    override val isLoading = false
    override val isSuccess = false
    override val isFailure = false

    override val asLoading: Loading<Nothing> get() = error("Pending state can't be casted to a Loading state")
    override val asSuccess: Success<Nothing> get() = error("Pending state can't be casted to a Success state")
    override val asFailure: Failure<Nothing> get() = error("Pending state can't be casted to a Failure state")

    override val data: Nothing? = null
    override fun toString(): String = "Pending"

    override fun <R> map(transformer: (Nothing) -> R): LazyState<R> = this
}