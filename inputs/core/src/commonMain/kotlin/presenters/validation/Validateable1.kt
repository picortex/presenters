package presenters.validation

interface Validateable1<in T> : Validateable0 {
    fun validate(value: T?): ValidationResult
    fun validateSettingInvalidsAsWarnings(value: T?): ValidationResult
    fun validateSettingInvalidsAsErrors(value: T?): ValidationResult
}