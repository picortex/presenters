package presenters.internal.utils

import live.MutableLive
import presenters.fields.InputFieldState
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

abstract class FeedbackSetter(private val feedback: MutableLive<InputFieldState>) {

    private fun setFeedbacks(res: ValidationResult, body: (res: Invalid) -> InputFieldState): ValidationResult {
        feedback.value = when (res) {
            is Invalid -> body(res)
            is Valid -> InputFieldState.Empty
        }
        return res
    }

    fun setFeedbacksAsWarnings(res: ValidationResult): ValidationResult {
        setFeedbacks(res) { InputFieldState.Warning(it.cause.message ?: "Unknown", it.cause) }
        return res
    }

    fun setFeedbacksAsErrors(res: ValidationResult): ValidationResult {
        setFeedbacks(res) { InputFieldState.Error(it.cause.message ?: "Unknown", it.cause) }
        return res
    }
}