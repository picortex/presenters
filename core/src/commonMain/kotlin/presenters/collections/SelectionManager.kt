@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters.collections

import live.MutableLive
import presenters.collections.internal.SelectionManagerImpl
import viewmodel.ViewModelConfig
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.jvm.JvmName
import kotlin.jvm.JvmStatic

@JsExport
interface SelectionManager<in T> {
    // ---------------------------------Selections--------------------------
    fun selectAllItemsInTheCurrentPage()

    fun selectAllItemsInPage(page: Int)

    fun selectAllItemsInAllPages()

    /**
     * Marks the item at row Number [row], in page [page] as selected
     * If there were other rows in the selected buffer, they will all be removed
     */
    @JsName("selectRowInPage")
    fun select(row: Int, page: Int)

    /**
     * Marks the item at row Number [row] in the current page
     * If there were other rows in the selected buffer, they will all be removed
     */
    @JsName("selectRow")
    fun select(row: Int)

    fun select(obj: T)

    // ---------------------------------Selection Adders --------------------------

    /**
     * Marks the [row] in page [page] as selected
     * If there were other items in the selected buffer, this [row] will be appended to the buffer
     */
    @JsName("addSelectionOfRowInPage")
    fun addSelection(row: Int, page: Int)

    /**
     * Marks the [row] as selected
     * If there were other items in the selected buffer, this [row] will be appended to the buffer
     */
    @JsName("addSelectionOfRowNumber")
    fun addSelection(row: Int)

    @JsName("addSelectionOf")
    fun addSelection(obj: T)

    // ---------------------------------Selection Toggles --------------------------

    fun toggleSelectionOfRowInPage(row: Int, page: Int)

    fun toggleSelectionOfRowInCurrentPage(row: Int)

    fun toggleSelectionOfPage(page: Int)

    fun toggleSelectionOfCurrentPage()

    // ---------------------------------Selection Checks--------------------------

    fun isPageSelectedWholly(page: Int): Boolean

    fun isPageSelectedPartially(page: Int): Boolean

    fun isCurrentPageSelectedWholly(): Boolean

    fun isCurrentPageSelectedPartially(): Boolean

    fun isRowSelectedOnCurrentPage(row: Int): Boolean

    fun isRowSelectedOnPage(row: Int, page: Int): Boolean

    // ---------------------------------UnSelections--------------------------
    fun unSelectAllItemsInAllPages()

    fun unSelectAllItemsInTheCurrentPage()

    fun unSelectAllItemsInPage(page: Int)

    /**
     * Unselects the item from row number [row] in the current page and effectively removes it from the selected buffer
     */
    fun unSelectRowInCurrentPage(row: Int)

    /**
     * Unselects the item from row number [row] in page [page] and effectively removes it from the selected buffer
     */
    fun unSelectRowInPage(row: Int, page: Int)

    // ---------------------------------SelectionGetters--------------------------
    val selected: Selected<@UnsafeVariance T>

    val state: MutableLive<SelectorState>

    companion object {
        @JvmStatic
        @JvmName("create")
        operator fun <T> invoke(
            paginator: PaginationManager<T>,
            config: ViewModelConfig = ViewModelConfig()
        ): SelectionManager<T> = SelectionManagerImpl(paginator, config)
    }
}