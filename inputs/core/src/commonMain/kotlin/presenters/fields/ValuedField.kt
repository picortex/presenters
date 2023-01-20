@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import presenters.InputField
import kotlin.js.JsExport

@Deprecated("Compose these values")
interface ValuedField<O> : InputField {
    val isReadonly: Boolean
    val isRequired: Boolean
    fun clear()
}