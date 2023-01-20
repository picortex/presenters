package presenters.internal.validators

import live.MutableLive
import presenters.fields.InputFieldState
import presenters.internal.utils.FeedbackSetter
import presenters.validation.Validateable1
import presenters.validation.ValidationResult

abstract class AbstractValidate1<in I>(
    feedback: MutableLive<InputFieldState>
) : FeedbackSetter(feedback), Validateable1<I> {

    abstract override fun validate(value: I?): ValidationResult

    override fun validateSettingInvalidsAsWarnings(value: I?) = setFeedbacks(validate(value)) {
        InputFieldState.Warning(it.cause.message ?: "", it.cause)
    }

    override fun validateSettingInvalidsAsErrors(value: I?) = setFeedbacks(validate(value)) {
        InputFieldState.Error(it.cause.message ?: "", it.cause)
    }
}