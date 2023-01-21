package presenters.internal.utils

import live.MutableLive
import presenters.InputFieldState
import presenters.properties.Clearable

class Clearer<out O>(
    private val value: O,
    private val data: MutableLive<O>,
    private val feedback: MutableLive<InputFieldState>,
) : Clearable {
    override fun clear() {
        data.value = value
        feedback.value = InputFieldState.Empty
    }
}