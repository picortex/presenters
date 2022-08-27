package presenters.modal.builders

import koncurrent.Later
import presenters.actions.SimpleActionsBuilder
import presenters.actions.SimpleAction

class ConfirmDialogActionsBuilder : SimpleActionsBuilder() {
    private var _confirmAction: SimpleAction? = null
    internal val confirmAction: SimpleAction get() = _confirmAction ?: error("Confirm Action has not yet been initialized")
    fun onConfirm(name: String = "Confirm", handler: () -> Later<Unit>): SimpleAction {
        val action = SimpleAction(name, handler)
        _confirmAction = action
        return action
    }
}