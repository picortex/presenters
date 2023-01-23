@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import kotlin.js.JsExport

interface Table<T> : DataCollection<T> {
    val columns: ColumnsManager<T>
    override fun manageActions(block: (ActionsManager<T>) -> Unit): Table<T>
    fun manageColumns(block: (manager: ColumnsManager<T>) -> Unit): Table<T>
    override fun <R> map(transform: (T) -> R): Table<R>
}