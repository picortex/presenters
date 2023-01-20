package presenters.internal

import live.MutableLive
import presenters.fields.InputFieldState
import presenters.fields.internal.AbstractValidate1
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.Validateable1
import presenters.validation.ValidationResult

internal class CompoundValidator<T>(
    override val feedback: MutableLive<InputFieldState>,
    vararg valigators: Validateable1<T>
) : AbstractValidate1<T>() {
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