@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.fields

import live.Live
import kotlin.js.JsExport

@JsExport
interface InputFieldWithValue<T> : InputField {
    val isReadonly: Boolean
    val isRequired: Boolean
    var value: T
    val feedback: Live<InputFieldState>
    val validator: (T) -> T
    fun validate()

    companion object {
        val DEFAULT_IS_READONLY = false
        val DEFAULT_IS_REQUIRED = false
    }
}