@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.fields

import live.Live
import kotlin.js.JsExport
import kotlin.js.JsName

interface SingleValuedField<in I, out O> : ValuedField<O?> {
    val input: Live<@UnsafeVariance I?>
    val transformer: (I?) -> O?
    val defaultValue: @UnsafeVariance I?

    fun validate(value: I? = input.value): ValidationResult
    fun validateSettingInvalidsAsWarnings(value: I? = input.value): ValidationResult
    fun validateSettingInvalidsAsErrors(value: I? = input.value): ValidationResult

    @JsName("setValue")
    fun set(value: I?)

    companion object {
        val DEFAULT_IS_READONLY = false
        val DEFAULT_IS_REQUIRED = false
        val DEFAULT_VALIDATOR: Nothing? = null
        val DEFAULT_VALUE: Nothing? = null
    }
}