@file:JsExport

package presenters.states

import kotlin.js.JsExport

object Pending : LazyState<Nothing> {
    override val isPending = true
    override val isLoading = false
    override val isSuccess = true
    override val isFailure = false
    override val asLoading: Loading<Nothing> get() = error(0)

    override val asSuccess: Success<Nothing> get() = error(0)
    override val asFailure: Failure<Nothing> get() = error(0)

    override val data: Nothing? = null
    override fun toString(): String = "Pending"
}