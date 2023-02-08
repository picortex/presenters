@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import kotlin.js.JsExport

sealed class Column<in D> {
    abstract val name: String
    abstract val key: String

    class Select(
        override val name: String,
        override val key: String,
    ) : Column<Any?>()

    class Data<in D>(
        override val name: String,
        override val key: String,
        val default: String,
        val accessor: (Row<D>) -> Any?
    ) : Column<D>() {
        fun resolve(row: Row<D>): String = accessor(row)?.toString() ?: default
    }

    class Action(
        override val name: String,
        override val key: String
    ) : Column<Any?>()

    val asSelect get() = this as? Select
    val asData get() = this as? Data
    val asAction get() = this as? Action

    override fun toString() = name
    override fun equals(other: Any?): Boolean = other is Column<Nothing> && other.name == name && other.key == key
}