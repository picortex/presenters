package presenters.fields.internal

import live.MutableLive
import presenters.fields.InputFieldState
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.Validateable1
import presenters.validation.ValidationResult

abstract class AbstractValidate1<in I> : Validateable1<I> {

    abstract override val feedback: MutableLive<InputFieldState>

    abstract override fun validate(value: I?): ValidationResult

    private fun validateSettingFeedback(value: I?, body: (res: Invalid) -> InputFieldState): ValidationResult {
        val res = validate(value)
        feedback.value = when (res) {
            is Invalid -> body(res)
            is Valid -> InputFieldState.Empty
        }
        return res
    }

    override fun validateSettingInvalidsAsWarnings(value: I?) = validateSettingFeedback(value) {
        InputFieldState.Warning(it.cause.message ?: "", it.cause)
    }

    override fun validateSettingInvalidsAsErrors(value: I?) = validateSettingFeedback(value) {
        InputFieldState.Error(it.cause.message ?: "", it.cause)
    }
}