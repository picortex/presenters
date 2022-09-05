package presenters.collections.internal

import koncurrent.Later
import kotlinx.collections.interoperable.List
import kotlinx.collections.interoperable.iListOf
import kotlinx.collections.interoperable.toInteroperableList
import live.Live
import live.MutableLive
import live.mutableLiveOf
import presenters.collections.*
import viewmodel.ViewModelConfig

class ScrollableListImpl<T>(
    override val paginator: PaginationManager<T>,
    override val selector: SelectionManager<T>,
    override val actionsManager: ActionsManager<T>,
    config: ViewModelConfig
) : PageableImpl<T>(config), ScrollableList<T>,
    PaginationManager<T>,
    SelectionManager<T> by selector,
    ActionsManager<T> by actionsManager {
    override val items: MutableLive<List<Row<T>>> = mutableLiveOf(iListOf())

    override val live: Live<PageableState<T>> get() = paginator.live
    override var capacity: Int
        get() = paginator.capacity
        set(value) {
            paginator.setPageCapacity(value)
        }

    override fun readPageFromMemory(page: Int, cap: Int): Page<T> = paginator.readPageFromMemory(page, cap)

    override fun readPageFromMemoryOrNull(page: Int, cap: Int): Page<T>? = paginator.readPageFromMemoryOrNull(page, cap)

    override fun writePageToMemory(page: Page<T>): Page<T>? = paginator.writePageToMemory(page)

    override fun setPageCapacity(cap: Int) = paginator.setPageCapacity(cap)

    override fun refresh() = updateListItemsAfter { refresh() }

    override fun loadNextPage() = updateListItemsAfter { loadNextPage() }

    override fun loadPreviousPage() = updateListItemsAfter { loadPreviousPage() }

    override fun loadPage(no: Int) = updateListItemsAfter { loadPage(no) }

    override fun loadFirstPage() = updateListItemsAfter { loadFirstPage() }

    override fun loadLastPage() = updateListItemsAfter { loadLastPage() }


    private inline fun updateListItemsAfter(block: PaginationManager<T>.() -> Later<Page<T>>): Later<Page<T>> {
        val later = paginator.block()
        later.then { updateListItems(it.number) }
        return later
    }

    private fun updateListItems(no: Int) {
        items.value = buildList {
            for (i in 1..no) {
                addAll(paginator.readPageFromMemory(i, capacity).items)
            }
        }.toInteroperableList()
    }
}