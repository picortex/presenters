@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.fields

import kotlin.js.JsExport

interface NumberBasedValuedField<out N : Number> : TextBasedValuedField<N>