@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import kotlin.js.JsExport

sealed class Column<in D> {
    abstract val name: String
    abstract val key: String
    abstract val index: Int
    val number get() = index + 1

    class Select(
        override val name: String,
        override val key: String,
        override val index: Int,
    ) : Column<Any?>()

    class Data<in D>(
        override val name: String,
        override val key: String,
        override val index: Int,
        val default: String,
        val accessor: (Row<D>) -> Any?
    ) : Column<D>() {
        fun resolve(row: Row<D>): String = accessor(row)?.toString() ?: default
    }

    class Action(
        override val name: String,
        override val key: String,
        override val index: Int
    ) : Column<Any?>()

    val asSelect get() = this as? Select
    val asData get() = this as? Data
    val asAction get() = this as? Action

    internal fun copy(
        name: String = this.name,
        index: Int = this.index
    ): Column<D> = when (this) {
        is Action -> Action(name, key, index)
        is Data -> Data(name, key, index, default, accessor)
        is Select -> Select(name, key, index)
    }

    override fun toString() = name
    override fun equals(other: Any?): Boolean = other is Column<Nothing> && other.name == name && other.key == key
}