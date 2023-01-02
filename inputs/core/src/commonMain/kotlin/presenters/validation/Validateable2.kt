package presenters.validation

interface Validateable2<in T> : Validateable0 {
    fun validate(start: T?, end: T?): ValidationResult
    fun validateSettingInvalidsAsWarnings(start: T?, end: T?): ValidationResult
    fun validateSettingInvalidsAsErrors(start: T?, end: T?): ValidationResult
}