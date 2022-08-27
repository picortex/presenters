package presenters.cases

import presenters.actions.SimpleActionsBuilder

fun <D> MissionState<D>.copy(
    message: String
): MissionState<D> = MissionState.Loading(
    message = message,
    data = data
)

fun <D> MissionState<D>.copy(
    cause: Throwable,
    builder: (SimpleActionsBuilder.() -> Unit)? = null
): MissionState<D> = MissionState.Failure(
    cause = cause,
    data = data,
    builder = builder
)

fun <D> MissionState<D>.copy(data: D): MissionState<D> = MissionState.Success(data)