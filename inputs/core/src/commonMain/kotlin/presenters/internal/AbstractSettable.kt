package presenters.internal

import live.MutableLive
import presenters.fields.InputFieldState
import presenters.fields.properties.Settable
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

abstract class AbstractSettable<V>(private val feedback: MutableLive<InputFieldState>) : Settable<V> {
    fun setFeedbacks(res: ValidationResult) {
        feedback.value = when (res) {
            is Invalid -> InputFieldState.Warning(res.cause.message ?: "Unknown", res.cause)
            is Valid -> InputFieldState.Empty
        }
    }
}