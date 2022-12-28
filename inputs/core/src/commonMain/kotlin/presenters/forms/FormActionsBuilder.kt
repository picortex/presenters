package presenters.forms

import actions.Action0I0RBuilder
import actions.Action1I1R
import actions.Action0I1RBuilder
import actions.action1I1RLater
import koncurrent.Later

class FormActionsBuilder<P, R> : Action0I0RBuilder() {
    private var _submitAction: Action1I1R<P, R>? = null
    val submitAction: Action1I1R<P, R> get() = _submitAction ?: error("Submit action has not been initialize just yet")
    fun onSubmit(name: String = "Submit", handler: (P) -> Later<R>): Action1I1R<P, R> {
        val action = action1I1RLater(name, handler)
        _submitAction = action
        return action
    }
}