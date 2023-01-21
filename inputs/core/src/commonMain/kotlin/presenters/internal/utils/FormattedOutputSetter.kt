package presenters.internal.utils

import live.MutableLive
import presenters.fields.FormattedData
import presenters.fields.InputFieldState
import presenters.properties.Settable
import presenters.validation.Validateable

class FormattedOutputSetter<I : Any, O : Any>(
    private val data: MutableLive<FormattedData<I, O>>,
    private val feedback: MutableLive<InputFieldState>,
    private val transformer: DataTransformer<I, O>,
    private val validator: Validateable<O>
) : FeedbackSetter(feedback), Settable<I> {
    override fun set(value: I) {
        val d = transformer.toFormattedData(value)
        data.value = d
        setFeedbacksAsWarnings(validator.validate(d.output))
    }
}