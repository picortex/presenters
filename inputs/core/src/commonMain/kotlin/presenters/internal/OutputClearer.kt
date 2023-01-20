package presenters.internal

import live.MutableLive
import presenters.OutputData
import presenters.fields.InputFieldState
import presenters.fields.internal.OutputData
import presenters.fields.properties.Clearable

class OutputClearer<V>(
    private val value: V?,
    private val data: MutableLive<OutputData<V>>,
    private val feedback: MutableLive<InputFieldState>,
) : Clearable {
    override fun clear() {
        data.value = OutputData(value)
        feedback.value = InputFieldState.Empty
    }
}