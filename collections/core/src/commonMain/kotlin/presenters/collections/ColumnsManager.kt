@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import kollections.List
import kotlin.js.JsExport

interface ColumnsManager<D> {
    val size: Int
    fun get(): List<Column<D>>
    fun add(name: String, accessor: (Row<D>) -> String): ColumnsManager<D>
    fun remove(name: String): ColumnsManager<D>
}