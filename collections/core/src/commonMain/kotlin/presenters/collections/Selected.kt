@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import kollections.Map
import kollections.Set
import kotlin.js.JsExport

sealed interface Selected<out T> {
    val none get() = this as? SelectedNone
    val item get() = this as? SelectedItem
    val items get() = this as? SelectedItems
    val global get() = this as? SelectedGlobal
}

object SelectedNone : Selected<Nothing>

data class SelectedItem<out T>(
    val page: Page<T>,
    val row: Row<T>
) : Selected<T>

data class SelectedItems<out T>(
    val page: Map<Page<@UnsafeVariance T>, Set<Row<T>>>
) : Selected<T>

data class SelectedGlobal<out T>(
    val exceptions: Set<SelectedItem<T>>
) : Selected<T>