package presenters.validation

import live.Live
import presenters.fields.InputFieldState

interface Validateable0 {
    val feedback: Live<InputFieldState>
    fun validate(): ValidationResult
    fun validateSettingInvalidsAsWarnings(): ValidationResult
    fun validateSettingInvalidsAsErrors(): ValidationResult
}