package presenters.fields.internal

import live.Live
import live.mutableLiveOf
import presenters.Label
import presenters.OutputData
import presenters.validation.ValidationResult

abstract class DirectInputValuedField<O : Any>(
    name: String,
    isRequired: Boolean,
    label: Label,
    defaultValue: @UnsafeVariance O?,
    isReadonly: Boolean,
    validator: ((O?) -> Unit)?,
) : AbstractSingleValuedField<O, O>(name, isRequired, label, defaultValue, isReadonly, validator) {
    private val _data  = mutableLiveOf(OutputData(defaultValue))
    val data : Live<OutputData<O>> get() = _data

    override fun set(value: O?) {
        setRaw(value)
        _data.value = RawData(value)
    }

    override fun clear() {
        _data.value = RawData(null)
        clearRaw()
    }

    abstract override fun validate(value: O?): ValidationResult

//    override fun validate() = validate(data.value.output)
//
//    override fun validateSettingInvalidsAsErrors() = validateSettingInvalidsAsErrors(data.value.output)
//
//    override fun validateSettingInvalidsAsWarnings() = validateSettingInvalidsAsWarnings(data.value.output)
}