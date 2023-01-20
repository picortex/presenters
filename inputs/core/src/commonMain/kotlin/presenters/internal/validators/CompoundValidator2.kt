package presenters.internal.validators

import live.MutableLive
import presenters.fields.InputFieldState
import presenters.internal.utils.FeedbackSetter
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.Validateable1
import presenters.validation.Validateable2
import presenters.validation.ValidationResult

class CompoundValidator2<in I>(
    override val feedback: MutableLive<InputFieldState>,
    vararg valigators: Validateable2<I>
) : AbstractValidate2<I>(feedback) {
    private val validators = valigators

    override fun validate(start: I?, end: I?): ValidationResult {
        var res: ValidationResult = Valid
        for (validator in validators) {
            res = validator.validate(start, end)
            if (res is Invalid) break
        }
        return res
    }
}