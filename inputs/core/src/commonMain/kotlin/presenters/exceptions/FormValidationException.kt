@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.exceptions

import kollections.List
import presenters.SerializableLiveData
import kotlin.js.JsExport

class FormValidationException(
    override val message: String,
    val errors: String,
    val fields: List<SerializableLiveData<out Any?>>
) : Throwable()