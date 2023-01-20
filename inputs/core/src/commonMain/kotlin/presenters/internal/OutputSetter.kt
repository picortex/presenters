package presenters.internal

import live.MutableLive
import presenters.OutputData
import presenters.fields.InputFieldState
import presenters.fields.internal.OutputData
import presenters.fields.properties.Settable
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.Validateable1

internal class OutputSetter<V>(
    private val data: MutableLive<OutputData<V>>,
    private val feedback: MutableLive<InputFieldState>,
    private val validator: Validateable1<V>
) : Settable<V> {
    override fun set(value: V) {
        data.value = OutputData(value)
        val res = validator.validate(value)
        feedback.value = when (res) {
            is Invalid -> InputFieldState.Warning(res.cause.message ?: "Unknown", res.cause)
            is Valid -> InputFieldState.Empty
        }
    }
}