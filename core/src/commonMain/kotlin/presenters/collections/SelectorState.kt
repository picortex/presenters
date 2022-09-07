@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import kotlinx.collections.interoperable.List
import kotlinx.collections.interoperable.iListOf
import kotlin.js.JsExport

sealed class SelectorState {
    object NoSelected : SelectorState()

    data class Item(val number: Int, val page: Int) : SelectorState()

    data class Items(val items: List<Item>) : SelectorState()

    data class AllSelected(val exceptions: List<Item>) : SelectorState()

    val isNoSelected get() = this is NoSelected
    val asNoSelected get() = this as NoSelected

    val isItem get() = this is Item
    val asItem get() = this as Item

    val isItems get() = this is Items
    val asItems get() = this as Items

    val isAllSelected get() = this is AllSelected
    val asAllSelected get() = this as AllSelected
}