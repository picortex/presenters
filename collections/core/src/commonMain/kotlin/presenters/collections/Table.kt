@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import kotlin.js.JsExport

interface Table<T> : DataCollection<T> {
    val columns: ColumnsManager<T>
}