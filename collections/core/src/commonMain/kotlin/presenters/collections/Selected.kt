@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import kollections.List
import kotlin.js.JsExport

sealed class Selected<out T>

object SelectedNone : Selected<Nothing>()

data class SelectedItem<out T>(
    val page: Page<T>,
    val row: Row<T>
) : Selected<T>()

data class SelectedPage<out T>(
    val page: Page<T>
) : Selected<T>()

data class SelectedItems<out T>(
    val values: List<SelectedItem<T>>
) : Selected<T>()

data class SelectedGlobal<out T>(
    val exceptions: List<Row<T>>
) : Selected<T>()