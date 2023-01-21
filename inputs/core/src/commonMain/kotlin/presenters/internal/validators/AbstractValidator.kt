package presenters.internal.validators

import live.Live
import live.MutableLive
import presenters.OutputData
import presenters.fields.InputFieldState
import presenters.internal.utils.FeedbackSetter
import presenters.validation.Validateable
import presenters.validation.ValidationResult

abstract class AbstractValidator<in I>(
    final override val data: Live<OutputData<@UnsafeVariance I>>,
    final override val feedback: MutableLive<InputFieldState>
) : FeedbackSetter(feedback), Validateable<@UnsafeVariance I> {

    abstract override fun validate(value: I?): ValidationResult

    override fun validateSettingInvalidsAsWarnings(value: I?) = setFeedbacksAsWarnings(validate(value))

    override fun validateSettingInvalidsAsErrors(value: I?) = setFeedbacksAsErrors(validate(value))
}