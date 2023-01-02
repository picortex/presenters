@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields.internal

import live.mutableLiveOf
import presenters.fields.InputLabel
import presenters.validation.ValidationResult
import kotlin.js.JsExport

abstract class DirectInputValuedField<O : Any>(
    name: String,
    isRequired: Boolean,
    label: InputLabel,
    defaultValue: @UnsafeVariance O?,
    isReadonly: Boolean,
    validator: ((O?) -> Unit)?,
) : AbstractValuedField<O, O>(name, isRequired, label, defaultValue, isReadonly, validator) {
    override val data = mutableLiveOf(OutputData(defaultValue))

    override fun set(value: O?) {
        setRaw(value)
        data.value = RawData(value)
    }

    override fun clear() {
        data.value = RawData(null)
        clearRaw()
    }

    abstract override fun validate(value: O?): ValidationResult

    override fun validate() = validate(data.value.output)

    override fun validateSettingInvalidsAsErrors() = validateSettingInvalidsAsErrors(data.value.output)

    override fun validateSettingInvalidsAsWarnings() = validateSettingInvalidsAsWarnings(data.value.output)
}