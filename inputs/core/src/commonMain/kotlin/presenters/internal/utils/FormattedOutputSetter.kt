package presenters.internal.utils

import live.MutableLive
import presenters.DataFormatted
import presenters.InputFieldState
import presenters.internal.FormattedData
import presenters.properties.Settable
import presenters.validation.Validateable

class FormattedOutputSetter<I : Any, O : Any>(
    private val data: MutableLive<DataFormatted<I, O>>,
    private val feedback: MutableLive<InputFieldState>,
    private val transformer: DataTransformer<I, O>,
    private val validator: Validateable<O>
) : FeedbackSetter(feedback), Settable<I> {
    override fun set(value: I?) {
        val prev = data.value
        try {
            val d = transformer.toFormattedData(value)
            data.value = d
            setFeedbacksAsWarnings(validator.validate(d.output))
        } catch (e: Throwable) {
            data.value = FormattedData(value, prev.formatted, prev.output)
            setWarning("Invalid value $value", e)
        }
    }
}