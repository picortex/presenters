@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields.internal

import kotlinx.serialization.KSerializer
import live.MutableLive
import live.mutableLiveOf
import presenters.fields.InputFieldState
import presenters.fields.InputLabel
import presenters.fields.Invalid
import presenters.fields.Valid
import presenters.fields.ValidationResult
import presenters.fields.ValuedField
import presenters.fields.ValuedField.Companion.DEFAULT_IS_READONLY
import presenters.fields.ValuedField.Companion.DEFAULT_IS_REQUIRED
import presenters.fields.ValuedField.Companion.DEFAULT_VALIDATOR
import presenters.fields.ValuedField.Companion.DEFAULT_VALUE
import kotlin.js.JsExport

abstract class AbstractValuedField<T>(
    override val name: String,
    override val isRequired: Boolean = DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name.replaceFirstChar { it.uppercase() }, isRequired),
    defaultValue: T? = DEFAULT_VALUE,
    override val isReadonly: Boolean = DEFAULT_IS_READONLY,
    val validator: ((T?) -> Unit)? = DEFAULT_VALIDATOR
) : ValuedField<T> {

    abstract val serializer: KSerializer<T>

    override val feedback = mutableLiveOf<InputFieldState>(InputFieldState.Empty)

    override val field: MutableLive<T?> = mutableLiveOf(defaultValue, 1)

    override fun set(value: T?) {
        val res = validate(value)
        feedback.value = when (res) {
            is Invalid -> InputFieldState.Warning(res.cause.message ?: "Unknown", res.cause)
            Valid -> InputFieldState.Empty
        }
        field.value = value
    }

    override fun clear() {
        field.value = null
        feedback.value = InputFieldState.Empty
    }

    abstract override fun validate(value: T?): ValidationResult

    private fun validateSettingFeedback(value: T?, body: (res: Invalid) -> InputFieldState): ValidationResult {
        val res = validate(value)
        feedback.value = when (res) {
            is Invalid -> body(res)
            Valid -> InputFieldState.Empty
        }
        return res
    }

    override fun validateSettingInvalidsAsWarnings(value: T?) = validateSettingFeedback(value) {
        InputFieldState.Warning(it.cause.message ?: "", it.cause)
    }

    override fun validateSettingInvalidsAsErrors(value: T?) = validateSettingFeedback(value) {
        InputFieldState.Error(it.cause.message ?: "", it.cause)
    }
}