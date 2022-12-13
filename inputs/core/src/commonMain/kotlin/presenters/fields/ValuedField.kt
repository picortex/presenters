@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.fields

import kotlinx.serialization.KSerializer
import live.Live
import kotlin.js.JsExport

sealed interface ValuedField<out O : Any> : InputField {
    val feedback: Live<InputFieldState>
    val output: Live<O?>
    val isReadonly: Boolean
    val isRequired: Boolean
    val serializer: KSerializer<@UnsafeVariance O>

    fun clear()

    companion object {
        val DEFAULT_IS_READONLY = false
        val DEFAULT_IS_REQUIRED = false
        val DEFAULT_VALIDATOR: Nothing? = null
        val DEFAULT_VALUE: Nothing? = null
    }
}