package presenters.validation

import live.Live
import presenters.fields.InputFieldState

interface Validateable2<in T> {
    val feedback: Live<InputFieldState>
    fun validate(start: T?, end: T?): ValidationResult
    fun validateSettingInvalidsAsWarnings(start: T?, end: T?): ValidationResult
    fun validateSettingInvalidsAsErrors(start: T?, end: T?): ValidationResult
}