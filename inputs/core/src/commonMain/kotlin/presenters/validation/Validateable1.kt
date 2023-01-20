package presenters.validation

import live.Live
import presenters.fields.InputFieldState

interface Validateable1<in T> {
    val feedback: Live<InputFieldState>
    fun validate(value: T?): ValidationResult
    fun validateSettingInvalidsAsWarnings(value: T?): ValidationResult
    fun validateSettingInvalidsAsErrors(value: T?): ValidationResult
}