@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields.internal

import live.MutableLive
import live.mutableLiveOf
import presenters.fields.FormattableData
import presenters.fields.InputLabel
import presenters.validation.ValidationResult
import kotlin.js.JsExport

abstract class TransformedInputValuedField<I, O : Any>(
    name: String,
    isRequired: Boolean,
    label: InputLabel,
    defaultValue: @UnsafeVariance I?,
    val formatter: ((O?) -> String?)?,
    val transformer: (I?) -> O?,
    isReadonly: Boolean,
    validator: ((I?) -> Unit)?,
) : AbstractValuedField<I, O>(name, isRequired, label, defaultValue, isReadonly, validator) {
    override val data: MutableLive<FormattableData<I, O>> = mutableLiveOf(toInputData(defaultValue))

    override fun set(value: I?) {
        setRaw(value)
        data.value = toInputData(value)
    }

    override fun clear() {
        clearRaw()
        data.value = toInputData(null)
    }

    abstract override fun validate(value: I?): ValidationResult

    override fun validate() = validate(data.value.raw)

    override fun validateSettingInvalidsAsErrors() = validateSettingInvalidsAsErrors(data.value.raw)

    override fun validateSettingInvalidsAsWarnings() = validateSettingInvalidsAsWarnings(data.value.raw)

    protected fun toInputData(value: I?): FormattableData<I, O> {
        val o = transformer(value)
        return FormattableData(
            raw = value,
            formatted = formatted(value, o),
            output = o
        )
    }

    protected fun formatted(input: I?, output: O?): String = formatter?.invoke(output) ?: input?.toString() ?: ""
}