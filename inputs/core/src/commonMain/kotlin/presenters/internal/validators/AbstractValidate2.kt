package presenters.internal.validators

import live.MutableLive
import presenters.fields.InputFieldState
import presenters.internal.utils.FeedbackSetter
import presenters.validation.Validateable1
import presenters.validation.Validateable2
import presenters.validation.ValidationResult

abstract class AbstractValidate2<in I>(
    feedback: MutableLive<InputFieldState>
) : FeedbackSetter(feedback), Validateable2<I> {

    abstract override fun validate(start: I?, end: I?): ValidationResult

    override fun validateSettingInvalidsAsWarnings(start: I?, end: I?) = setFeedbacks(validate(start, end)) {
        InputFieldState.Warning(it.cause.message ?: "", it.cause)
    }

    override fun validateSettingInvalidsAsErrors(start: I?, end: I?) = setFeedbacks(validate(start, end)) {
        InputFieldState.Error(it.cause.message ?: "", it.cause)
    }
}