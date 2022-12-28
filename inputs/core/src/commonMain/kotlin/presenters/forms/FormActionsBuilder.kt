package presenters.forms

import actions.GenericAction
import actions.SimpleActionsBuilder
import actions.genericActionOfLater
import koncurrent.Later

class FormActionsBuilder<P,R> : SimpleActionsBuilder<R>() {
    private var _submitAction: GenericAction<P,R>? = null
    val submitAction: GenericAction<P,R> get() = _submitAction ?: error("Submit action has not been initialize just yet")
    fun onSubmit(name: String = "Submit", handler: (P) -> Later<R>): GenericAction<P,R> {
        val action = genericActionOfLater(name, handler)
        _submitAction = action
        return action
    }
}