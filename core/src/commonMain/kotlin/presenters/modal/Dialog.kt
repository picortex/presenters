@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.modal

import kotlinx.collections.interoperable.List
import presenters.actions.SimpleAction
import kotlin.js.JsExport

@JsExport
sealed interface Dialog<out F, in P> {
    val heading: String
    val details: String
    val cancel: SimpleAction
    val actions: List<SimpleAction>

    val isForm get() = this is FormDialog
    val asForm get() = this as FormDialog

    val isConfirm get() = this is ConfirmDialog
    val asConfirm get() = this as ConfirmDialog
}