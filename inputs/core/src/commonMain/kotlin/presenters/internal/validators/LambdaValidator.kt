package presenters.internal.validators

import live.Live
import live.MutableLive
import presenters.OutputData
import presenters.fields.InputFieldState
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

class LambdaValidator<T>(
    data: Live<OutputData<T>>,
    feedback: MutableLive<InputFieldState>,
    private val lambda: ((T?) -> Unit)?
) : AbstractValidator<T>(data, feedback) {
    override fun validate(value: T?): ValidationResult = try {
        lambda?.invoke(value)
        Valid
    } catch (err: Throwable) {
        Invalid(err)
    }
}