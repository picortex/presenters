@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters

import kollections.Collection
import presenters.validation.Validateable0
import kotlin.js.JsExport

sealed interface ChoiceField<O> : InputField, Validateable0 {
    val items: Collection<O>
}