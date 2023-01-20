package presenters.internal.validators

import live.MutableLive
import presenters.fields.InputFieldState
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

class LambdaValidator<T>(
    override val feedback: MutableLive<InputFieldState>,
    private val lambda: ((T?) -> Unit)?
) : AbstractValidate1<T>(feedback) {
    override fun validate(value: T?): ValidationResult = try {
        lambda?.invoke(value)
        Valid
    } catch (err: Throwable) {
        Invalid(err)
    }
}