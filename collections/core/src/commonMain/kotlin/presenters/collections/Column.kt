@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import kotlin.js.JsExport

sealed class Column<in D> {
    abstract val name: String

    data class Select(
        override val name: String,
    ) : Column<Nothing>()

    data class Data<in D>(
        override val name: String,
        val accessor: (Row<D>) -> String = { "" }
    ) : Column<D>()

    data class Action(
        override val name: String
    ) : Column<Nothing>()

    val isSelect get() = this is Select
    val asSelect get() = this as Select

    val isData get() = this is Data
    val asData get() = this as Data

    val isAction get() = this is Action
    val asAction get() = this as Action
}