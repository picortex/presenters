package presenters.validation

interface Validateable0 {
    fun validate(): ValidationResult
    fun validateSettingInvalidsAsWarnings(): ValidationResult
    fun validateSettingInvalidsAsErrors(): ValidationResult
}