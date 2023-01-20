package presenters.internal

import live.MutableLive
import live.mutableLiveOf
import presenters.fields.InputFieldState
import presenters.fields.internal.AbstractValidate1
import presenters.fields.properties.Settable
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

internal class LambdaValidator<T>(
    override val feedback: MutableLive<InputFieldState>,
    private val lambda: ((T?) -> Unit)?
) : AbstractValidate1<T>() {
    override fun validate(value: T?): ValidationResult = try {
        lambda?.invoke(value)
        Valid
    } catch (err: Throwable) {
        Invalid(err)
    }
}