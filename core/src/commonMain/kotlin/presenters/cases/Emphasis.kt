@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "FunctionName")

package presenters.cases

import presenters.actions.SimpleActionsBuilder
import presenters.forms.BaseForm
import presenters.modal.Dialog
import presenters.modal.FormDialog
import kotlin.js.JsExport
import kotlin.js.JsName
import presenters.cases.Failure as FailureCase
import presenters.cases.Success as SuccessCase

@Deprecated("Coz it is using old dialog API")
sealed class Emphasis {
    companion object {
        @Deprecated("Coz it is using old dialog API")
        fun Loading(message: String) = Status(Feedback.Loading(message))

        @Deprecated("Coz it is using old dialog API")
        fun Success(
            message: String = SuccessCase.DEFAULT_MESSAGE,
            builder: (SimpleActionsBuilder.() -> Unit)? = null
        ) = Status(Feedback.Success(message, builder))

        @Deprecated("Coz it is using old dialog API")
        fun Failure(
            cause: Throwable? = null,
            message: String = cause?.message ?: FailureCase.DEFAULT_MESSAGE,
            builder: (SimpleActionsBuilder.() -> Unit)? = null
        ) = Status(Feedback.Failure(cause, message, builder))

        @Deprecated("Coz it is using old dialog API")
        @JsName("fromDialog")
        fun Dialog(dialog: Dialog<*, *>) = Modal(dialog)

        @Deprecated("Coz it is using old dialog API")
        @JsName("fromForm")
        fun Dialog(form: BaseForm<*, *>) = Modal(FormDialog(form))
    }

    @Deprecated("Coz it is using old dialog API")
    object None : Emphasis()

    @Deprecated("Coz it is using old dialog API")
    data class Status(val feedback: Case) : Emphasis()

    @Deprecated("Coz it is using old dialog API")
    data class Modal(val dialog: Dialog<*, *>) : Emphasis()

    val isStatus get() = this is Status
    val asStatus get() = this as Status

    val isNone get() = this is None

    val isLoading get() = this is Status && feedback.isLoading
    val asLoading get() = (this as Status).feedback.asLoading

    val isSuccess get() = this is Status && feedback.isSuccess
    val asSuccess get() = (this as Status).feedback.asSuccess

    val isFailure get() = this is Status && feedback.isFailure
    val asFailure get() = (this as Status).feedback.asFailure

    val isDialog get() = this is Modal
    val asDialog get() = (this as Modal).dialog
}
