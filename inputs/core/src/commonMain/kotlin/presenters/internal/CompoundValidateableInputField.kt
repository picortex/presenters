package presenters.internal

import live.mutableLiveOf
import presenters.InputFieldState
import presenters.internal.utils.Clearer
import presenters.internal.utils.OutputSetter
import presenters.internal.validators.AbstractValidator
import presenters.internal.validators.CompoundValidator
import presenters.internal.validators.LambdaValidator
import presenters.internal.validators.RequirementValidator

abstract class CompoundValidateableInputField<O> : AbstractValidator<O>(mutableLiveOf(InputFieldState.Empty)) {
    abstract val cv: CompoundValidator<O>
    override fun validate(value: O?) = cv.validate(value)
    override fun validateSettingInvalidsAsErrors(value: O?) = cv.validateSettingInvalidsAsErrors(value)
    override fun validateSettingInvalidsAsWarnings(value: O?) = cv.validateSettingInvalidsAsWarnings(value)
}