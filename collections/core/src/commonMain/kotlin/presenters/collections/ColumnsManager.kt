@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import kollections.Set
import live.Live
import kotlin.js.JsExport

interface ColumnsManager<D> {
    val current: Live<Set<Column<D>>>
    fun all(includingRemoved: Boolean = false): Set<Column<D>>
    fun add(name: String, accessor: (Row<D>) -> String): ColumnsManager<D>
    fun remove(name: String): ColumnsManager<D>
    fun hide(name: String): ColumnsManager<D>
    fun show(name: String): ColumnsManager<D>
    fun rename(prev: String, curr: String): ColumnsManager<D>
    fun index(name: String, idx: Int): ColumnsManager<D>
}