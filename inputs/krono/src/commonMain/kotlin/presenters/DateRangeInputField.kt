@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters

import krono.LocalDate
import presenters.fields.Range
import kotlin.js.JsExport

interface DateRangeInputField : TransformingRangeInputField<String, LocalDate> {
    override val start: DateInputField
    override val end: DateInputField
}