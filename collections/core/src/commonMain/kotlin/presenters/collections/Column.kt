@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import kotlin.js.JsExport

sealed class Column<in D> {
    abstract val name: String

    class Select(
        override val name: String,
    ) : Column<Any?>()

    class Data<in D>(
        override val name: String,
        val accessor: (Row<D>) -> String
    ) : Column<D>()

    class Action(
        override val name: String
    ) : Column<Any?>()

    val asSelect get() = this as? Select
    val asData get() = this as? Data
    val asAction get() = this as? Action

    override fun toString() = name
    override fun equals(other: Any?): Boolean = other is Column<*> && other.name == name
}