package presenters.internal

import live.MutableLive
import presenters.OutputData
import presenters.fields.FormattedData
import presenters.fields.InputFieldState
import presenters.fields.internal.OutputData
import presenters.fields.properties.Settable
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.Validateable1

internal class FormattedOutputSetter<I : Any, O : Any>(
    private val data: MutableLive<FormattedData<I, O>>,
    private val feedback: MutableLive<InputFieldState>,
    private val transformer: DataTransformer<I, O>,
    private val validator: Validateable1<O>
) : AbstractSettable<I>(feedback) {
    override fun set(value: I) {
        val d = transformer.toFormattedData(value)
        data.value = d
        setFeedbacks(validator.validate(d.output))
    }
}