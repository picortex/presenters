package presenters.cases

import presenters.actions.SimpleActionsBuilder
import presenters.modal.Dialog

fun <D> GenericState<D>.copy(dialog: Dialog<*, *>?): GenericState<D> = when (this) {
    is GenericState.Content -> copy(dialog = dialog)
    else -> this
}

fun <D> GenericState<D>.copy(data: D): GenericState<D> = when (this) {
    is GenericState.Content -> copy(data = data, dialog = this.dialog)
    else -> GenericState.Content(data, dialog = null)
}

fun <D> GenericState<D>.loading(message: String, data: D? = null): GenericState<D> = GenericState.Loading(
    message = message, data ?: this.data
)

fun <D> GenericState<D>.success(
    message: String = Success.DEFAULT_MESSAGE,
    data: D? = null,
    builder: SimpleActionsBuilder.() -> Unit
): GenericState<D> = GenericState.Success(message, data ?: this.data, builder)

fun <D> GenericState<D>.failure(
    cause: Throwable? = null,
    data: D? = null,
    builder: SimpleActionsBuilder.() -> Unit
): GenericState<D> = GenericState.Failure(cause, data = data ?: this.data, builder = builder)
