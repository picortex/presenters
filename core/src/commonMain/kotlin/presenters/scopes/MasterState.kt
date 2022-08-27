@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.scopes

import kotlinx.collections.interoperable.List
import presenters.actions.SimpleAction
import presenters.actions.SimpleActionsBuilder
import presenters.cases.Case
import presenters.collections.ScrollableList
import presenters.collections.Table
import kotlin.js.JsExport
import kotlin.js.JsName
import presenters.cases.Failure as FailureCase
import presenters.cases.Loading as LoadingCase

sealed class MasterState<out D> : Case {
    data class Loading(
        override val message: String
    ) : MasterState<Nothing>(), LoadingCase

    data class Loaded<out D>(
        val items: List<D>,
        val list: ScrollableList<out D>,
        val table: Table<out D>
    ) : MasterState<D>() {
        override val message: String = "Loaded"
    }

    class Failure(
        override val cause: Throwable? = null,
        override val message: String = cause?.message ?: FailureCase.DEFAULT_MESSAGE,
        override val actions: List<SimpleAction>
    ) : MasterState<Nothing>(), FailureCase {
        @JsName("_ignore_")
        constructor(
            cause: Throwable? = null,
            message: String = cause?.message ?: FailureCase.DEFAULT_MESSAGE,
            builder: (SimpleActionsBuilder.() -> Unit)? = null
        ) : this(cause, message, builder?.let { SimpleActionsBuilder().apply(it).actions } ?: kotlinx.collections.interoperable.emptyList())

        override val failure: Boolean = true
    }

    override val isLoading get() = this is Loading
    override val asLoading get() = this as Loading

    val isLoaded get() = this is Loaded
    val asLoaded get() = this as Loaded

    override val isFailure get() = this is Failure
    override val asFailure get() = this as Failure
}