@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.fields

import kotlinx.serialization.KSerializer
import live.Live
import presenters.validation.Validateable1
import kotlin.js.JsExport

interface ValuedField<O> : InputField {
    val feedback: Live<InputFieldState>
    val data: Live<OutputData<O>>
    val isReadonly: Boolean
    val isRequired: Boolean
    val serializer: KSerializer<O>

    fun clear()
}