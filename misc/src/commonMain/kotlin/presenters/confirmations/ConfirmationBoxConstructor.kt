package presenters.confirmations

import presenters.confirmations.internal.ConfirmationBoxImpl
import viewmodel.ScopeConfig

fun ConfirmationBox(
    heading: String,
    details: String,
    message: String = "Executing, please wait . . .",
    config: ScopeConfig<Any?>,
    actionsBuilder: ConfirmActionsBuilder.() -> Unit
): ConfirmationBox = ConfirmationBoxImpl(heading, details, message, config, actionsBuilder)