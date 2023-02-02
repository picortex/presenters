@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "OPT_IN_USAGE", "WRONG_EXPORTED_DECLARATION")

package presenters.collections

import functions.Function1
import functions.Function2
import kollections.List
import kotlin.js.JsExport
import kotlin.js.JsName


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

    val hasMore: Boolean

    val isFistPage: Boolean

    val isLastPage: Boolean

    @JsName("_ignore_map")
    fun <R> map(transformer: Function1<T, R>): Page<R>

    @JsName("_ignore_mapIndexed")
    fun <R> mapIndexed(transformer: Function2<Int, T, R>): Page<R>

    fun <R> map(transformer: (item: T) -> R): Page<R>

    fun <R> mapIndexed(transformer: (index: Int, item: T) -> R): Page<R>
}