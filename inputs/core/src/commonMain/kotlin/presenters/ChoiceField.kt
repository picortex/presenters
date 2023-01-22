@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters

import kollections.Collection
import kotlin.js.JsExport

sealed interface ChoiceField<O> : InputField, CommonInputProperties {
    val items: Collection<O>
}