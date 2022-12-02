@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.fields

import live.Live
import kotlin.js.JsExport

@JsExport
interface ValuedField<out T : Any> : InputField {
    val input: Live<T?>
    val feedback: Live<InputFieldState>

    val defaultValue: T?
    val value: T?
    val isReadonly: Boolean
    val isRequired: Boolean

    val labelWithAsterisks get() = label.replaceFirstChar { it.uppercase() } + if (isRequired) "*" else ""
    val asteriskedLabel get() = labelWithAsterisks

    fun validate(value: @UnsafeVariance T? = this.value)
    fun validateWithFeedback(value: @UnsafeVariance T? = this.value)
    fun clear()

    companion object {
        val DEFAULT_IS_READONLY = false
        val DEFAULT_IS_REQUIRED = false
        val DEFAULT_VALIDATOR: Nothing? = null
        val DEFAULT_VALUE: Nothing? = null
    }
}