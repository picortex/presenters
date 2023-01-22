@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.validation

import live.Live
import presenters.Data
import presenters.InputFieldState
import presenters.LiveData
import kotlin.js.JsExport

interface Validateable<T> : LiveData<T> {
    val feedback: Live<InputFieldState>
    fun validate(value: T? = data.value.output): ValidationResult
    fun validateSettingInvalidsAsWarnings(value: T? = data.value.output): ValidationResult
    fun validateSettingInvalidsAsErrors(value: T? = data.value.output): ValidationResult
}