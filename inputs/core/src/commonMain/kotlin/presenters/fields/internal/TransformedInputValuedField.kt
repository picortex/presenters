package presenters.fields.internal

import live.MutableLive
import live.mutableLiveOf
import presenters.fields.FormattedData
import presenters.fields.Formatter
import presenters.fields.InputLabel
import presenters.fields.TransformedInput
import presenters.validation.ValidationResult

abstract class TransformedInputValuedField<I, O : Any>(
    name: String,
    isRequired: Boolean,
    label: InputLabel,
    defaultValue: @UnsafeVariance I?,
    override val formatter: Formatter<O>?,
    final override val transformer: (I?) -> O?,
    isReadonly: Boolean,
    validator: ((I?) -> Unit)?,
) : AbstractSingleValuedField<I, O>(name, isRequired, label, defaultValue, isReadonly, validator), TransformedInput<I, O> {
    override val data: MutableLive<FormattedData<I, O>> = mutableLiveOf(toFormattedData(defaultValue))

    override fun set(value: I?) {
        setRaw(value)
        data.value = toFormattedData(value)
    }

    override fun clear() {
        clearRaw()
        data.value = toFormattedData(null)
    }

    abstract override fun validate(value: I?): ValidationResult

    override fun validate() = validate(data.value.raw)

    override fun validateSettingInvalidsAsErrors() = validateSettingInvalidsAsErrors(data.value.raw)

    override fun validateSettingInvalidsAsWarnings() = validateSettingInvalidsAsWarnings(data.value.raw)

    fun toFormattedData(value: I?): FormattedData<I, O> {
        val o = transformer(value)
        return FormattedData(
            raw = value,
            formatted = formatted(value, o),
            output = o
        )
    }

    protected fun formatted(input: I?, output: O?): String = formatter?.invoke(output) ?: input?.toString() ?: ""
}