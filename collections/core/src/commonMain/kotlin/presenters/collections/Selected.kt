@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import kollections.Map
import kollections.Set
import kotlin.js.JsExport

sealed class Selected<out T>

object SelectedNone : Selected<Nothing>()

data class SelectedItem<out T>(
    val page: Page<T>,
    val row: Row<T>
) : Selected<T>()

data class SelectedItems<T>(
    val values: Map<Page<T>, Set<Row<T>>>
) : Selected<T>()

data class SelectedGlobal<out T>(
    val exceptions: Set<SelectedItem<T>>
) : Selected<T>()