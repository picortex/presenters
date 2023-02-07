package presenters.confirmations

import actions.Action0
import actions.builders.Actions0Builder
import actions.action0I1RLater
import koncurrent.Later

class ConfirmActionsBuilder : Actions0Builder<Unit>() {
    private var _submitAction: Action0<Later<Any?>>? = null
    val submitAction: Action0<Later<Any?>> get() = _submitAction ?: error("Submit action has not been initialize just yet")
    fun onConfirm(name: String = "Confirm", handler: () -> Later<Any?>): Action0<Any?> {
        val action = action0I1RLater(name, handler = handler)
        _submitAction = action
        return action
    }
}