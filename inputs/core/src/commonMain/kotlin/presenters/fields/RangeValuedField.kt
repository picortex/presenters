@file:JsExport @file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.fields

import kotlin.js.JsExport

interface RangeValuedField<in I, out O> : ValuedField<Range<O>?> {
    val start: SingleValuedField<I, O>
    val end: SingleValuedField<I, O>
    val transformer: (I?) -> O?
    val limit: Range<O>?
    fun setStart(value: I?)
    fun setEnd(value: I?)
    fun validate(start: I? = this.start.input.value, end: I? = this.end.input.value): ValidationResult
    fun validateSettingInvalidsAsWarnings(start: I? = this.start.input.value, end: I? = this.end.input.value): ValidationResult
    fun validateSettingInvalidsAsErrors(start: I? = this.start.input.value, end: I? = this.end.input.value): ValidationResult
}