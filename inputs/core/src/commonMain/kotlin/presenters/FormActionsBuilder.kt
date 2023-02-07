package presenters

import actions.Action1
import actions.builders.Actions0Builder
import actions.action1I1RLater
import koncurrent.Later

class FormActionsBuilder<P, R> : Actions0Builder<Unit>() {
    private var _submitAction: Action1<P, Later<R>>? = null
    val submitAction: Action1<P, Later<R>> get() = _submitAction ?: error("Submit action has not been initialize just yet")
    fun onSubmit(name: String = "Submit", handler: (P) -> Later<R>): Action1<P, Later<R>> {
        val action = action1I1RLater(name, handler = handler)
        _submitAction = action
        return action
    }
}