package presenters.validation

import live.Live
import presenters.LiveOutputData
import presenters.OutputData
import presenters.fields.InputFieldState

interface Validateable<T> {
    val data: Live<OutputData<T>>
    val feedback: Live<InputFieldState>
    fun validate(value: T? = data.value.output): ValidationResult
    fun validateSettingInvalidsAsWarnings(value: T? = data.value.output): ValidationResult
    fun validateSettingInvalidsAsErrors(value: T? = data.value.output): ValidationResult
}