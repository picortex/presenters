@file:JsExport @file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import kollections.List
import kotlin.js.JsExport

interface Table<T> : DataCollection<T> {
    val columns: List<Column<T>>
}