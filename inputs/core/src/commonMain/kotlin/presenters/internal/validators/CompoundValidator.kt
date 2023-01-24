package presenters.internal.validators

import live.Live
import live.MutableLive
import presenters.Data
import presenters.InputFieldState
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

class CompoundValidator<T>(
    override val data: Live<Data<T>>,
    override val feedback: MutableLive<InputFieldState>,
    vararg valigators: AbstractValidator<T>
) : AbstractValidator<T>(feedback) {
    private val validators = valigators

    override fun validate(value: T?): ValidationResult {
        var res: ValidationResult = Valid
        for (validator in validators) {
            res = validator.validate(value)
            if (res is Invalid) break
        }
        return res
    }
}