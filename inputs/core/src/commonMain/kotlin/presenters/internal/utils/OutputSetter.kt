package presenters.internal.utils

import live.MutableLive
import presenters.OutputData
import presenters.InputFieldState
import presenters.internal.OutputData
import presenters.properties.Settable
import presenters.validation.Validateable

internal class OutputSetter<V>(
    private val data: MutableLive<OutputData<V>>,
    private val feedback: MutableLive<InputFieldState>,
    private val validator: Validateable<V>
) : FeedbackSetter(feedback), Settable<V> {
    override fun set(value: V) {
        data.value = OutputData(value)
        setFeedbacksAsWarnings(validator.validate(value))
    }
}