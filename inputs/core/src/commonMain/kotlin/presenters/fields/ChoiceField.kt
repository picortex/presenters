@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.fields

import kollections.Collection
import presenters.validation.Validateable0
import kotlin.js.JsExport

sealed interface ChoiceField<out O> : Validateable0 {
    val items: Collection<O>
}