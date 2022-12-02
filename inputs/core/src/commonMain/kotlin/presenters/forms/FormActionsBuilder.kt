package presenters.forms

import koncurrent.Later
import presenters.actions.GenericAction
import presenters.actions.SimpleActionsBuilder

class FormActionsBuilder<P> : SimpleActionsBuilder() {
    private var _submitAction: GenericAction<P>? = null
    val submitAction: GenericAction<P> get() = _submitAction ?: error("Submit action has not been initialize just yet")
    fun onSubmit(name: String = "Submit", handler: (P) -> Later<Any?>): GenericAction<P> {
        val action = GenericAction.ofLater(name, handler)
        _submitAction = action
        return action
    }
}