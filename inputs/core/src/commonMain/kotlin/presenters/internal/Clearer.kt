package presenters.internal

import live.MutableLive
import presenters.OutputData
import presenters.fields.InputFieldState
import presenters.fields.internal.OutputData
import presenters.fields.properties.Clearable

class Clearer<O>(
    private val value: O,
    private val data: MutableLive<O>,
    private val feedback: MutableLive<InputFieldState>,
) : Clearable {
    override fun clear() {
        data.value = value
        feedback.value = InputFieldState.Empty
    }
}