package presenters.internal.validators

import live.MutableLive
import presenters.InputFieldState
import presenters.internal.utils.FeedbackSetter
import presenters.validation.Validateable
import presenters.validation.ValidationResult

abstract class AbstractValidator<I>(override val feedback: MutableLive<InputFieldState>) : FeedbackSetter(feedback), Validateable<I> {

    abstract override fun validate(value: I?): ValidationResult

    override fun validateSettingInvalidsAsWarnings(value: I?) = setFeedbacksAsWarnings(validate(value))

    override fun validateSettingInvalidsAsErrors(value: I?) = setFeedbacksAsErrors(validate(value))
}