package presenters.internal.utils

import live.MutableLive
import presenters.OutputData
import presenters.fields.InputFieldState
import presenters.fields.internal.OutputData
import presenters.fields.properties.Settable
import presenters.validation.Validateable1

internal class OutputSetter<V>(
    private val data: MutableLive<OutputData<V>>,
    private val feedback: MutableLive<InputFieldState>,
    private val validator: Validateable1<V>
) : FeedbackSetter(feedback), Settable<V> {
    override fun set(value: V) {
        data.value = OutputData(value)
        setFeedbacks(validator.validate(value))
    }
}