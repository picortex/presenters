@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.states

import presenters.actions.SimpleAction
import presenters.actions.SimpleActionsBuilder
import presenters.states.Result
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
data class Failure<out D>(
    val cause: Throwable? = null,
    val message: String = cause?.message ?: DEFAULT_MESSAGE,
    override val data: D? = null,
    val actions: List<SimpleAction>
) : LazyState<D>, MissionState<D>, Result<D>, Case, Feedback {
    @JsName("_ignore_builder")
    constructor(
        cause: Throwable? = null,
        message: String = cause?.message ?: DEFAULT_MESSAGE,
        data: D? = null,
        builder: (SimpleActionsBuilder.() -> Unit)? = null
    ) : this(cause, message, data, builder?.let { SimpleActionsBuilder().apply(it).actions } ?: emptyList())

    companion object {
        val DEFAULT_MESSAGE = "Unknown error"
    }

    override val isPending = false
    override val isLoading = false
    override val isSuccess = false
    override val isFailure = true

    override val asLoading: Loading<D> get() = error(0)
    override val asSuccess: Success<D> get() = error(0)
    override val asFailure: Failure<D> get() = this

    override fun <R> map(transformer: (D) -> R): Failure<R> = if (data != null) {
        Failure(cause, message, transformer(data), actions)
    } else {
        Failure(cause, message, null, actions)
    }
}