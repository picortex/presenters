package presenters.modal

import koncurrent.Later

fun Dialog<*, *>.click(name: String): Later<out Any?> {
    if (this is ConfirmDialog && confirm.name == name) return run {
        confirm.handler()
        Later.resolve(Unit)
    }

    val action = actions.firstOrNull {
        it.name == name
    } ?: error(
        """Action "$name" is not found in dialog "$heading".${"\n"}Available actions are ${actions.joinToString(prefix = "[", postfix = "]") { it.name }}"""
    )
    action.handler()
    return Later.resolve(Unit)
}

fun <P> FormDialog<*, P>.clickSubmit(params: P) = submit.handler(params)

fun ConfirmDialog.clickSubmit() = click("Submit")

fun <P> Dialog<*, P>.clickSubmit(with: P) = when (this) {
    is ConfirmDialog -> clickSubmit()
    is FormDialog -> clickSubmit(params = with)
}