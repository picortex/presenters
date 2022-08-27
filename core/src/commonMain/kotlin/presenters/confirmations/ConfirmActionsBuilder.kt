package presenters.confirmations

import koncurrent.Later
import presenters.actions.SimpleAction
import presenters.actions.SimpleActionsBuilder

class ConfirmActionsBuilder : SimpleActionsBuilder() {
    private var _submitAction: SimpleAction? = null
    val submitAction: SimpleAction get() = _submitAction ?: error("Submit action has not been initialize just yet")
    fun onConfirm(name: String = "Confirm", handler: () -> Later<Any?>): SimpleAction {
        val action = SimpleAction.ofLater(name, handler)
        _submitAction = action
        return action
    }
}