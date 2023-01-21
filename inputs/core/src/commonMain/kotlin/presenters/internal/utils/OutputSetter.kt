package presenters.internal.utils

import kollections.List
import kollections.toIList
import live.MutableLive
import presenters.OutputData
import presenters.InputFieldState
import presenters.internal.OutputData
import presenters.internal.OutputList
import presenters.properties.Settable
import presenters.validation.Validateable

class OutputSetter<V>(
    private val data: MutableLive<OutputData<V>>,
    private val feedback: MutableLive<InputFieldState>,
    private val validator: Validateable<V>
) : FeedbackSetter(feedback), Settable<V> {
    override fun set(value: V) {
        data.value = when (value) {
            is List<*> -> OutputList(value) as OutputData<V>
            is Collection<*> -> OutputList(value.toIList()) as OutputData<V>
            else -> OutputData(value)
        }
        setFeedbacksAsWarnings(validator.validate(value))
    }
}