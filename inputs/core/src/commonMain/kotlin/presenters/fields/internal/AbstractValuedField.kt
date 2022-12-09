@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields.internal

import live.MutableLive
import live.mutableLiveOf
import presenters.fields.InputFieldState
import presenters.fields.InputLabel
import presenters.fields.Invalid
import presenters.fields.SingleValuedField
import presenters.fields.SingleValuedField.Companion.DEFAULT_IS_READONLY
import presenters.fields.SingleValuedField.Companion.DEFAULT_IS_REQUIRED
import presenters.fields.SingleValuedField.Companion.DEFAULT_VALIDATOR
import presenters.fields.SingleValuedField.Companion.DEFAULT_VALUE
import presenters.fields.Valid
import presenters.fields.ValidationResult
import kotlin.js.JsExport

abstract class AbstractValuedField<in I, out O>(
    override val name: String,
    override val isRequired: Boolean = DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    override val defaultValue: @UnsafeVariance I? = DEFAULT_VALUE,
    override val transformer: (I?) -> O?,
    override val isReadonly: Boolean = DEFAULT_IS_READONLY,
    val validator: ((I?) -> Unit)? = DEFAULT_VALIDATOR,
    override val input: MutableLive<@UnsafeVariance I?> = mutableLiveOf(defaultValue),
    override val output: MutableLive<@UnsafeVariance O?> = input.map { transformOrNull(it, transformer) }
) : SingleValuedField<I, O> {
    override val feedback = mutableLiveOf<InputFieldState>(InputFieldState.Empty)

    override fun set(value: I?) {
        val res = validate(value)
        feedback.value = when (res) {
            is Invalid -> InputFieldState.Warning(res.cause.message ?: "Unknown", res.cause)
            Valid -> InputFieldState.Empty
        }
        input.value = value
    }

    override fun clear() {
        input.value = null
        feedback.value = InputFieldState.Empty
    }

    abstract override fun validate(value: I?): ValidationResult

    private fun validateSettingFeedback(value: I?, body: (res: Invalid) -> InputFieldState): ValidationResult {
        val res = validate(value)
        feedback.value = when (res) {
            is Invalid -> body(res)
            Valid -> InputFieldState.Empty
        }
        return res
    }

    override fun validateSettingInvalidsAsWarnings(value: I?) = validateSettingFeedback(value) {
        InputFieldState.Warning(it.cause.message ?: "", it.cause)
    }

    override fun validateSettingInvalidsAsErrors(value: I?) = validateSettingFeedback(value) {
        InputFieldState.Error(it.cause.message ?: "", it.cause)
    }
}