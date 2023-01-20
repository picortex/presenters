package presenters.fields.internal

import live.mutableLiveOf
import presenters.fields.Formatter
import presenters.fields.InputFieldState
import presenters.Label
import presenters.fields.TransformingValuedField
import presenters.validation.Invalid
import presenters.validation.Valid

abstract class AbstractTransformingValuedField<I, O>(
    val name: String,
    val isRequired: Boolean,
    val label: Label,
    val isReadonly: Boolean,
    final override val formatter: Formatter<O>?,
    final override val transformer: (I?) -> O?,
    val validator: ((O?) -> Unit)?,
) : AbstractValidate1<O>(), TransformingValuedField<I, O> {
    final override val feedback = mutableLiveOf<InputFieldState>(InputFieldState.Empty)

    protected fun setRaw(value: I?) {
        val res = validate(transformer(value))
        feedback.value = when (res) {
            is Invalid -> InputFieldState.Warning(res.cause.message ?: "Unknown", res.cause)
            is Valid -> InputFieldState.Empty
        }
    }

    protected fun clearRaw() {
        feedback.value = InputFieldState.Empty
    }
}