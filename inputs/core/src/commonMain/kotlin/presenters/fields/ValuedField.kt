@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.fields

import live.Live
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
interface ValuedField<T> : InputField {
    val field: Live<T?>
    val feedback: Live<InputFieldState>

    val defaultValue: T?

    val isReadonly: Boolean
    val isRequired: Boolean

    val labelWithAsterisks get() = label.replaceFirstChar { it.uppercase() } + if (isRequired) "*" else ""
    val asteriskedLabel get() = labelWithAsterisks

    fun validate(value: @UnsafeVariance T? = field.value)
    fun validateWithFeedback(value: @UnsafeVariance T? = field.value)
    fun clear()

    @JsName("setValue")
    fun set(value: T?)

    companion object {
        val DEFAULT_IS_READONLY = false
        val DEFAULT_IS_REQUIRED = false
        val DEFAULT_VALIDATOR: Nothing? = null
        val DEFAULT_VALUE: Nothing? = null
    }
}