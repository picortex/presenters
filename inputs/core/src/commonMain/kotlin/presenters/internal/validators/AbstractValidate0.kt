package presenters.internal.validators

import live.MutableLive
import presenters.fields.InputFieldState
import presenters.internal.utils.FeedbackSetter
import presenters.validation.Validateable0
import presenters.validation.ValidationResult

abstract class AbstractValidate0(
    final override val feedback: MutableLive<InputFieldState>
) : FeedbackSetter(feedback), Validateable0  {

    abstract override fun validate(): ValidationResult

    override fun validateSettingInvalidsAsWarnings() = setFeedbacks(validate()) {
        InputFieldState.Warning(it.cause.message ?: "", it.cause)
    }

    override fun validateSettingInvalidsAsErrors() = setFeedbacks(validate()) {
        InputFieldState.Error(it.cause.message ?: "", it.cause)
    }
}