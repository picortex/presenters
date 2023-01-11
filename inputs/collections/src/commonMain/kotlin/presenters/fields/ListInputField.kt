@file:JsExport

package presenters.fields

import kollections.List
import presenters.validation.Validateable1
import kotlin.js.JsExport

interface ListInputField<E> : ValuedField<List<E>> {
    val minItems: Int?
    val maxItems: Int?
    fun add(item: E)
    fun remove(item: E)
}