@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters

import krono.LocalDate
import presenters.properties.Typeable
import kotlin.js.JsExport

interface DateInputField : TransformingInputField<String, LocalDate>, Typeable {
    val pattern: String
    val max: LocalDate?
    val min: LocalDate?
}