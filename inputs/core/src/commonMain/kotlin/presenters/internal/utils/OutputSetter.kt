package presenters.internal.utils

import kollections.toIList
import live.MutableLive
import presenters.Data
import presenters.InputFieldState
import presenters.internal.OutputData
import presenters.internal.OutputList
import presenters.properties.Settable
import presenters.validation.Validateable

class OutputSetter<in V>(
    private val data: MutableLive<Data<V>>,
    private val feedback: MutableLive<InputFieldState>,
    private val validator: Validateable<V>
) : FeedbackSetter(feedback), Settable<V> {
    override fun set(value: V?) {
        data.value = when (value) {
            is Collection<Any?> -> OutputList(value.toIList()) as Data<V>
            else -> OutputData(value)
        }
        setFeedbacksAsWarnings(validator.validate(value))
    }
}