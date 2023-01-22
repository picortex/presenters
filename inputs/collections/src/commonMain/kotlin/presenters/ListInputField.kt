@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters

import kollections.List
import presenters.properties.Clearable
import presenters.properties.Hintable
import presenters.properties.Labeled
import presenters.properties.Mutability
import presenters.properties.Requireble
import presenters.properties.Settable
import presenters.validation.Validateable
import kotlin.js.JsExport

interface ListInputField<E> : Labeled, Hintable, Mutability, Requireble, LiveOutputList<E>, Settable<List<E>>, Validateable<List<E>>, Clearable {
    val minItems: Int?
    val maxItems: Int?
    fun add(item: E)
    fun addAll(items: List<E>)
    fun remove(item: E)
    fun removeAll(items: List<E> = data.value.output)
    fun update(item: E, updater: () -> E)
}