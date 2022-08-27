@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.cases

import kotlinx.collections.interoperable.List
import kotlinx.collections.interoperable.emptyList
import presenters.actions.SimpleAction
import presenters.actions.SimpleActionsBuilder
import kotlin.js.JsExport
import kotlin.js.JsName
import presenters.cases.Failure as FailureCase
import presenters.cases.Loading as LoadingCase
import presenters.cases.Success as SuccessCase

@Deprecated("In favour of presenters.states")
sealed class MissionState<out T> : Case {
    abstract val data: T?

    @Deprecated("In favour of presenters.states")
    data class Loading<out D>(
        override val message: String,
        override val data: D? = null,
    ) : MissionState<D>(), LoadingCase

    @Deprecated("In favour of presenters.states")
    data class Failure<out D>(
        override val cause: Throwable? = null,
        override val message: String = cause?.message ?: FailureCase.DEFAULT_MESSAGE,
        override val data: D? = null,
        override val actions: List<SimpleAction>
    ) : MissionState<D>(), FailureCase {
        @JsName("_ignore_builder")
        constructor(
            cause: Throwable? = null,
            message: String = cause?.message ?: FailureCase.DEFAULT_MESSAGE,
            data: D? = null,
            builder: (SimpleActionsBuilder.() -> Unit)? = null
        ) : this(cause, message, data, builder?.let { SimpleActionsBuilder().apply(it).actions } ?: emptyList())

        override val failure: Boolean = true
    }

    @Deprecated("In favour of presenters.states")
    data class Success<out D>(
        override val data: D
    ) : MissionState<D>(), SuccessCase {
        override val message: String = SuccessCase.DEFAULT_MESSAGE
        override val actions: List<SimpleAction> = emptyList()
    }

    override val isLoading get() = this is Loading
    override val asLoading get() = this as Loading

    override val isSuccess get() = this is Success
    override val asSuccess get() = this as Success

    override val isFailure get() = this is Failure
    override val asFailure get() = this as Failure
}