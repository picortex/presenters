@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields.internal

import live.mutableLiveOf
import presenters.fields.InputFieldState
import presenters.fields.SingleValuedField
import presenters.fields.ValuedField.Companion.DEFAULT_IS_READONLY
import presenters.fields.ValuedField.Companion.DEFAULT_IS_REQUIRED
import presenters.fields.ValuedField.Companion.DEFAULT_VALIDATOR
import presenters.fields.SingleValuedField.Companion.DEFAULT_VALUE
import kotlin.js.JsExport

abstract class AbstractSingleValuedField<T : Any>(
    override val name: String,
    override val label: String = name.replaceFirstChar { it.uppercase() },
    override val defaultValue: T? = DEFAULT_VALUE,
    override val isReadonly: Boolean = DEFAULT_IS_READONLY,
    override val isRequired: Boolean = DEFAULT_IS_REQUIRED,
    override val validator: ((T?) -> Unit)? = DEFAULT_VALIDATOR
) : SingleValuedField<T> {
    override val feedback = mutableLiveOf<InputFieldState>(InputFieldState.Empty)

    override var value: T? = null
        get() = field ?: defaultValue
        set(value) {
            update(value)
            field = value
        }

    private fun update(value: T?) {
        try {
            validate(value)
            if (feedback.value != InputFieldState.Empty) {
                feedback.value = InputFieldState.Empty
            }
        } catch (err: Throwable) {
            feedback.value = InputFieldState.Warning(err.message ?: "", err)
        }
    }

    override fun clear() {
        value = null
        feedback.value = InputFieldState.Empty
    }

    abstract override fun validate(value: T?)

    override fun validateWithFeedback(value: T?) {
        try {
            validate(value)
            if (feedback.value != InputFieldState.Empty) {
                feedback.value = InputFieldState.Empty
            }
        } catch (err: Throwable) {
            feedback.value = InputFieldState.Error(err.message ?: "", err)
        }
    }
}