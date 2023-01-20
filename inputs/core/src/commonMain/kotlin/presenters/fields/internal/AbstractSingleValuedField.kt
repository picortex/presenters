package presenters.fields.internal

import live.mutableLiveOf
import presenters.fields.InputFieldState
import presenters.Label
import presenters.fields.SingleValuedField
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

abstract class AbstractSingleValuedField<I, O>(
    final override val name: String,
    final override val isRequired: Boolean,
    val label: Label,
    final override val defaultValue: @UnsafeVariance I?,
    final override val isReadonly: Boolean,
    val validator: ((I?) -> Unit)?,
) : SingleValuedField<I, O> {
    final override val feedback = mutableLiveOf<InputFieldState>(InputFieldState.Empty)

    protected fun setRaw(value: I?) {
        val res = validate(value)
        feedback.value = when (res) {
            is Invalid -> InputFieldState.Warning(res.cause.message ?: "Unknown", res.cause)
            is Valid -> InputFieldState.Empty
        }
    }

    protected fun clearRaw() {
        feedback.value = InputFieldState.Empty
    }

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