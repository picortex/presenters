@file:Suppress("WRONG_EXPORTED_DECLARATION", "NON_EXPORTABLE_TYPE")

package presenters.cases

import kotlinx.collections.interoperable.List
import presenters.actions.SimpleAction
import kotlin.js.JsExport
import kotlin.jvm.JvmField

@JsExport
@Deprecated("In favour of presenters.states.Case")
interface Case {
    val message: String

    val isLoading get() = this is Loading
    val asLoading get() = this as Loading

    val isSuccess get() = this is Success
    val asSuccess get() = this as Success

    val isFailure get() = this is Failure
    val asFailure get() = this as Failure
}

@JsExport
@Deprecated("In favour of presenters.states.Loading")
interface Loading : Case {
    val loading: Boolean get() = true // TODO: Check to see if this is still needed in the client side
}

@JsExport
@Deprecated("In favour of presenters.states.Failure")
interface Failure : Case {
    val cause: Throwable?
    val actions: List<SimpleAction>
    val failure get() = true  // TODO: Check to see if this is still needed in the client side

    companion object {
        @JvmField
        val DEFAULT_MESSAGE = "Unknown error"
    }
}

@JsExport
@Deprecated("In favour of presenters.states.Success")
interface Success : Case {
    val actions: List<SimpleAction>
    val success get() = true  // TODO: Check to see if this is still needed in the client side

    companion object {
        @JvmField
        val DEFAULT_MESSAGE = "Success"
    }
}