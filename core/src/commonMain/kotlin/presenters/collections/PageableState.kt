@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import kotlinx.collections.interoperable.List
import kotlinx.collections.interoperable.emptyList
import presenters.actions.SimpleAction
import presenters.actions.SimpleActionsBuilder
import presenters.cases.Case
import kotlin.js.JsExport
import kotlin.js.JsName
import presenters.cases.Failure as FailureCase
import presenters.cases.Loading as LoadingCase

sealed class PageableState<out T> : Case {
    abstract val page: Page<T>?

    data class UnLoaded<out T>(
        override val page: Page<T>? = null
    ) : PageableState<T>()

    data class Loading<out T>(
        override val message: String,
        override val page: Page<T>? = null
    ) : PageableState<T>(), LoadingCase

    data class LoadedPage<out T>(
        override val page: Page<T>
    ) : PageableState<T>(), Page<T> by page

    data class Failure<out T>(
        override val cause: Throwable? = null,
        override val message: String = cause?.message ?: FailureCase.DEFAULT_MESSAGE,
        override val page: Page<T>? = null,
        override val actions: List<SimpleAction>
    ) : PageableState<T>(), FailureCase {
        @JsName("_ignore_builder")
        constructor(
            cause: Throwable? = null,
            message: String = cause?.message ?: FailureCase.DEFAULT_MESSAGE,
            page: Page<T>? = null,
            builder: (SimpleActionsBuilder.() -> Unit)? = null
        ) : this(cause, message, page, builder?.let { SimpleActionsBuilder().apply(it).actions } ?: emptyList())

        override val failure: Boolean = true
    }

    val isUnLoaded get() = this is UnLoaded
    val asUnloaded get() = this as UnLoaded

    override val isLoading get() = this is Loading
    override val asLoading get() = this as Loading

    val isLoadedPage get() = this is LoadedPage
    val asLoadedPage get() = this as LoadedPage
    val asLoadedPageOrNull get() = this as? LoadedPage

    override val isFailure get() = this is Failure
    override val asFailure get() = this as Failure

    override val message: String = "PagedState"

    val currentPage get() = asLoadedPage.page
    val currentPageOrNull get() = asLoadedPageOrNull?.page
}