package presenters.confirmations.internal

import actions.mutableAction0I0R
import kase.Executing
import kase.ExecutorState
import kase.Failure
import kase.Pending
import kase.Success
import koncurrent.FailedLater
import live.MutableLive
import live.mutableLiveOf
import presenters.confirmations.ConfirmActionsBuilder
import presenters.confirmations.ConfirmationBox
import viewmodel.BaseViewModel
import viewmodel.ScopeConfig

@PublishedApi
internal class ConfirmationBoxImpl(
    override val heading: String,
    override val details: String,
    val executionMessage: String,
    config: ScopeConfig<Any?>,
    actionsBuilder: ConfirmActionsBuilder.() -> Unit
) : BaseViewModel(config), ConfirmationBox {

    private val actions = ConfirmActionsBuilder().apply(actionsBuilder)

    override val state: MutableLive<ExecutorState<Unit>> = mutableLiveOf(Pending, 2)

    override val cancelAction = mutableAction0I0R("Cancel") {
        val action = actions.actions.firstOrNull {
            it.name.contentEquals("cancel", ignoreCase = true)
        }?.handler ?: {
            logger.warn("Cancel hasn't been handled yet")
        }
        action()
    }

    private val confirmAction = actions.submitAction

    override fun cancel() {
        try {
            cancelAction()
        } catch (cause: Throwable) {
            state.value = Failure(cause)
        }
    }

    override fun confirm() = try {
        state.value = Executing(message = executionMessage)
        confirmAction()
    } catch (err: Throwable) {
        FailedLater(err)
    }.then {
        state.value = Success(Unit)
    }.catch {
        state.value = Failure(it)
        throw it
    }
}