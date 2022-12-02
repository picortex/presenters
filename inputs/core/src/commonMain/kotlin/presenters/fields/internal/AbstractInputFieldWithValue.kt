@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields.internal

import live.mutableLiveOf
import presenters.fields.InputFieldState
import presenters.fields.InputFieldWithValue
import presenters.fields.InputFieldWithValue.Companion.DEFAULT_IS_READONLY
import presenters.fields.InputFieldWithValue.Companion.DEFAULT_IS_REQUIRED
import kotlin.js.JsExport

@Deprecated("In favour of AbstractValuedField")
abstract class AbstractInputFieldWithValue<T>(
    override val name: String,
    override val label: String = name,
    value: T,
    override val isReadonly: Boolean = DEFAULT_IS_READONLY,
    override val isRequired: Boolean = DEFAULT_IS_REQUIRED,
    override val validator: (T) -> T = { it }
) : InputFieldWithValue<T> {
    override val feedback = mutableLiveOf<InputFieldState>(InputFieldState.Empty)

    override var value: T = value
        set(value) {
            if (feedback.value != InputFieldState.Empty) {
                feedback.value = InputFieldState.Empty
            }
            field = value
        }

    override fun validate() {
        try {
            validator(value)
            feedback.value = InputFieldState.Valid
        } catch (err: Throwable) {
            feedback.value = InputFieldState.Error(err.message ?: "Invalid input $value for field $label", err)
        }
    }

    val asteriskedLabel get() = labelWithAsterisks

    val labelWithAsterisks get() = (if (isRequired) "*" else "") + label.replaceFirstChar { it.uppercase() }
}