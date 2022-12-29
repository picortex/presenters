package presenters.confirmations.internal

import kase.Executing
import kase.ExecutorState
import kase.Failure
import kase.Loading
import kase.Pending
import kase.Success
import koncurrent.FailedLater
import koncurrent.Later
import live.MutableLive
import live.mutableLiveOf
import presenters.actions.MutableSimpleAction
import presenters.confirmations.ConfirmActionsBuilder
import presenters.confirmations.ConfirmationBox
import viewmodel.BaseViewModel
import viewmodel.ScopeConfig

@PublishedApi
internal class ConfirmationBoxImpl(
    override val heading: String,
    override val details: String,
    val executionMessage: String,
    config: ScopeConfig<*>,
    actionsBuilder: ConfirmActionsBuilder.() -> Unit
) : BaseViewModel(config), ConfirmationBox {

    private val actions = ConfirmActionsBuilder().apply(actionsBuilder)

    override val state: MutableLive<ExecutorState<Unit>> = mutableLiveOf(Pending, 2)

    override val cancelAction = MutableSimpleAction(
        name = "Cancel",
        handler = actions.actions.firstOrNull {
            it.name.contentEquals("cancel", ignoreCase = true)
        }?.handler ?: {
            logger.warn("Cancel hasn't been handled yet")
        }
    )

    private val confirmAction = actions.submitAction

    override fun cancel(): Later<Any?> = try {
        cancelAction()
    } catch (cause: Throwable) {
        FailedLater(cause)
    }

    override fun confirm(): Later<Any?> = try {
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