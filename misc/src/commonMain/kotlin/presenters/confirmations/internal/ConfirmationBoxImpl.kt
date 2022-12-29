package presenters.confirmations.internal

import koncurrent.Later
import koncurrent.FailedLater
import koncurrent.later.catch
import live.Live
import live.MutableLive
import live.mutableLiveOf
import presenters.actions.MutableSimpleAction
import presenters.confirmations.ConfirmActionsBuilder
import presenters.confirmations.ConfirmationBox
import presenters.confirmations.ConfirmationState
import presenters.states.Failure
import presenters.states.LazyState
import presenters.states.Loading
import presenters.states.Pending
import presenters.states.Success
import viewmodel.BaseViewModel
import viewmodel.ScopeConfig
import viewmodel.ViewModel

@PublishedApi
internal class ConfirmationBoxImpl(
    override val heading: String,
    override val details: String,
    val executionMessage: String,
    config: ScopeConfig<*>,
    actionsBuilder: ConfirmActionsBuilder.() -> Unit
) : BaseViewModel(config), ConfirmationBox {

    private val actions = ConfirmActionsBuilder().apply(actionsBuilder)

    override val state: MutableLive<LazyState<Unit>> = mutableLiveOf(Pending, 2)

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
        state.value = Loading(executionMessage)
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