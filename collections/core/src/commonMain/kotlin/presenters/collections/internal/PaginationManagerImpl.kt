package presenters.collections.internal

import kollections.toIList
import koncurrent.FailedLater
import koncurrent.Later
import live.MutableLive
import live.mutableLiveOf
import presenters.collections.Page
import presenters.collections.PaginationManager
import presenters.collections.Row
import kase.Failure
import kase.LazyState
import kase.Loading
import kase.Pending
import kase.Success
import kase.toLazyState
import koncurrent.later.finally

@PublishedApi
internal class PaginationManagerImpl<T>(
    override var capacity: Int,
    internal val loader: (no: Int, capacity: Int) -> Later<Page<T>>
) : PaginationManager<T> {

    private val cache: PageCacheManager<T> = PageCacheManager()

    override val current: MutableLive<LazyState<Page<T>>> = mutableLiveOf(Pending)

    override val currentPageOrNull get() = current.value.data

    override val hasMore: Boolean get() = currentPageOrNull?.hasMore == true

    override val continuous
        get() = buildList {
            forEachPage { page ->
                addAll(page.items.mapIndexed { index, row -> Row(index * page.number, row.item) })
            }
        }.toIList()

    override fun forEachPage(block: (Page<T>) -> Unit) {
        cache.records[capacity]?.pages?.values?.sortedBy { it.number }?.forEach(block)
    }

    override fun find(item: T) = cache.load(item, capacity)

    override fun wipeMemory() = cache.clear()

    override fun clearPages() {
        wipeMemory()
        current.value = Pending
    }

    override fun setPageCapacity(cap: Int) {
        capacity = cap
    }

    override fun loadNextPage() = when (val state = current.value) {
        is Pending -> loadPage(1)
        is Loading -> loadNothing()
        is Failure -> loadPage(1)
        is Success -> when {
            state.data.isEmpty -> Later(state.data)
            state.data.items.size < state.data.capacity -> Later(state.data)
            else -> loadPage(state.data.number + 1)
        }
    }

    override fun loadPreviousPage() = when (val state = current.value) {
        is Pending -> loadPage(1)
        is Loading -> loadNothing()
        is Failure -> loadPage(1) // FailedLater(RESOLVE_ERROR)
        is Success -> when {
            state.data.number > 1 -> loadPage(state.data.number - 1)
            else -> loadPage(1)
        }
    }

    override fun loadPage(no: Int): Later<Page<T>> {
        if (current.value is Loading) return FailedLater(LOADING_ERROR)
        val memorizedPage = cache.load(page = no, capacity)
        current.value = Loading("Loading", memorizedPage)
        return try {
            loader(no, capacity)
        } catch (err: Throwable) {
            FailedLater(err)
        }.then {
            cache.save(it)
        }.finally {
            current.value = it.toLazyState(memorizedPage)
        }
    }

    override fun refresh() = when (val state = current.value) {
        is Pending -> loadPage(1)
        is Loading -> loadNothing()
        is Failure -> loadPage(1)
        is Success -> loadPage(state.data.number)
    }

    private fun loadNothing() = Later(Unit)

    override fun loadFirstPage(): Later<Page<T>> = loadPage(1)

    override fun loadLastPage(): Later<Page<T>> = loadPage(-1)

    override fun find(row: Int, page: Int) = cache.load(row, page, capacity)

    override fun find(page: Int) = cache.load(page, capacity)

    override fun <R> map(transform: (T) -> R): PaginationManager<R> = PaginationManagerImpl(capacity) { no, capacity ->
        loader(no, capacity).then { it.map(transform) }
    }

    companion object {
        val DEFAULT_CAPACITY = 10

        val RESOLVE_ERROR = Throwable("Can't resolve page number while paginator is in a failure state")
        val LOADING_ERROR = Throwable("Can't load page while paginator is still loading")
    }
}