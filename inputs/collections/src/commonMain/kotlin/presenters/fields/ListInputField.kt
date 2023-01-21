@file:JsExport

package presenters.fields

import kollections.List
import kotlin.js.JsExport

interface ListInputField<E> : ValuedField<List<E>> {
    val minItems: Int?
    val maxItems: Int?
    fun add(item: E)
    fun remove(item: E)
    fun update(item: E, updater: () -> E)
}