@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.fields

import kollections.Collection
import kotlin.js.JsExport

sealed interface ChoiceField<out O> : Validateable0 {
    val items: Collection<O>
}