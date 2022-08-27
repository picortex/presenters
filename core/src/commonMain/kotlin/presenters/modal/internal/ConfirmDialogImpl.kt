@file:Suppress("EqualsOrHashCode")

package presenters.modal.internal

import presenters.actions.SimpleAction
import kotlinx.collections.interoperable.List
import presenters.modal.ConfirmDialog

open class ConfirmDialogImpl(
    override val heading: String,
    override val details: String,
    override val actions: List<SimpleAction>,
    override val confirm: SimpleAction
) : ConfirmDialog {
    override val isForm = false
    override val isConfirm = true

    override val cancel by lazy {
        actions.firstOrNull {
            it.name.contentEquals("cancel", ignoreCase = true)
        } ?: error("No cancel action has been registered to FormDialog(heading=$heading)")
    }

    override val asConfirm get() = this

    override fun equals(other: Any?): Boolean = other is ConfirmDialog && other.heading == heading && other::class == this::class
}