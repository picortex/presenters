@file:JsExport

package presenters.fields

import kotlin.js.JsExport

interface BooleanInputField : SingleValuedField<Boolean, Boolean> {
    fun toggle()
}