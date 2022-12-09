@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.fields

import kollections.List
import kotlin.js.JsExport

interface MultiChoiceValuedField<out O> : ChoiceField<O>, ValuedField<List<O>>