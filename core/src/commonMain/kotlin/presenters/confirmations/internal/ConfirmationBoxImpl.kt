package presenters.confirmations.internal

import koncurrent.Later
import koncurrent.later.catch
import presenters.actions.ActionsBuilder
import presenters.actions.MutableSimpleAction
import presenters.actions.SimpleActionsBuilder
import presenters.confirmations.ConfirmActionsBuilder
import presenters.confirmations.ConfirmationBox
import presenters.confirmations.ConfirmationState
import presenters.forms.FormActionsBuilder
import viewmodel.ScopeConfig
import viewmodel.ViewModel

@PublishedApi
internal class ConfirmationBoxImpl(
    override val heading: String,
    override val details: String,
    config: ScopeConfig<*>,
    actionsBuilder: ConfirmActionsBuilder.() -> Unit
) : ViewModel<ConfirmationState>(config.of(ConfirmationState.Pending)), ConfirmationBox {

    private val actions = ConfirmActionsBuilder().apply(actionsBuilder)

    override val cancelAction = MutableSimpleAction(
        name = "Cancel",
        handler = actions.actions.firstOrNull {
            it.name.contentEquals("cancel", ignoreCase = true)
        }?.handler ?: {
            logger.warn("Cancel hasn't been handled yet")
        }
    )

    private val confirmAction = actions.submitAction

    override fun confirm(): Later<Any?> = try {
        ui.value = ConfirmationState.Executing
        confirmAction()
    } catch (err: Throwable) {
        Later.reject(err)
    }.then {
        ui.value = ConfirmationState.Executed.Successfully
    }.catch {
        ui.value = ConfirmationState.Executed.Exceptionally
        throw it
    }
}