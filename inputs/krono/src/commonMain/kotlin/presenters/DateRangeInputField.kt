@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters

import krono.LocalDate
import kotlin.js.JsExport

interface DateRangeInputField : TransformingRangeInputField<String, LocalDate> {
    override val start: DateInputField
    override val end: DateInputField
    val pattern: String
    val max: LocalDate?
    val min: LocalDate?
}