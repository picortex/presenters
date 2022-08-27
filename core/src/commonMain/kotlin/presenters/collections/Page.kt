@file:Suppress("NON_EXPORTABLE_TYPE", "OPT_IN_USAGE", "WRONG_EXPORTED_DECLARATION")

package presenters.collections

import functions.Function
import kotlinx.collections.interoperable.List
import kotlinx.collections.interoperable.iListOf
import kotlinx.collections.interoperable.toInteroperableList
import presenters.collections.internal.PageImpl
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.jvm.JvmName
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

@JsExport
/**
 * A model representation of what a Page of data should contain
 */
interface Page<out T> {
    /**
     * The items of elements the page contains
     */
    val items: List<Row<T>>

    /**
     * The capacity of the contained items.
     * This will always equal [items].size, except for last pages
     * e.g. The last page can have 7 items, while the page can hold up to 10 items.
     * if ([items].size < [capacity]) is satisfied, one can safely assume that this is the last page.
     */
    val capacity: Int

    /**
     * The page [number] of this whole collection
     */
    val number: Int

    /**
     * Checks to see weather the page is empty
     */
    val isEmpty: Boolean

    @JsName("_ignore_map")
    fun <R> map(transformer: Function<T, R>): Page<R>

    @JsName("_ignore_mapIndexed")
    fun <R> mapIndexed(transformer: Function2<Int, T, R>): Page<R>

    fun <R> map(transformer: (item: T) -> R): Page<R>

    fun <R> mapIndexed(transformer: (index: Int, item: T) -> R): Page<R>

    companion object {
        @JvmStatic
        @JvmOverloads
        @JvmName("create")
        operator fun <T> invoke(
            items: Collection<T> = iListOf(),
            capacity: Int = items.size,
            no: Int = 1
        ): Page<T> = PageImpl(
            items = items.mapIndexed { index, it ->
                Row(index, it)
            }.toInteroperableList(),
            capacity = capacity,
            number = no
        )
    }
}