@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields.internal

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationStrategy
import live.MutableLive
import live.mutableLiveOf
import presenters.fields.InputFieldState
import presenters.fields.ValuedField
import presenters.fields.ValuedField.Companion.DEFAULT_IS_READONLY
import presenters.fields.ValuedField.Companion.DEFAULT_IS_REQUIRED
import presenters.fields.ValuedField.Companion.DEFAULT_VALIDATOR
import presenters.fields.ValuedField.Companion.DEFAULT_VALUE
import kotlin.js.JsExport

abstract class AbstractValuedField<T : Any>(
    override val name: String,
    override val label: String = name.replaceFirstChar { it.uppercase() },
    defaultValue: T? = DEFAULT_VALUE,
    override val isReadonly: Boolean = DEFAULT_IS_READONLY,
    override val isRequired: Boolean = DEFAULT_IS_REQUIRED,
    val validator: ((T?) -> Unit)? = DEFAULT_VALIDATOR
) : ValuedField<T> {

    abstract val serializer: KSerializer<T>

    override val feedback = mutableLiveOf<InputFieldState>(InputFieldState.Empty)

    override val input: MutableLive<T?> = mutableLiveOf(defaultValue, 1)

    override var value: T?
        get() = input.value
        set(value) {
            update(value)
            input.value = value
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