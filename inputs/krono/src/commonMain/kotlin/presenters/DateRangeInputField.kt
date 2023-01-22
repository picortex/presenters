@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters

import krono.LocalDate
import presenters.properties.Bounded
import presenters.properties.Patterned
import kotlin.js.JsExport

interface DateRangeInputField : TransformingRangeInputField<String, LocalDate>, Bounded<LocalDate>, Patterned {
    override val start: DateOutputField
    override val end: DateInputField
}