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
import presenters.collections.SelectedItem

@PublishedApi
internal class PaginationManagerImpl<T>(
    override var capacity: Int,
    private var loader: (no: Int, capacity: Int) -> Later<Page<T>>
) : PaginationManager<T> {

    private val cache: PageCacheManager<T> = PageCacheManager()

    override val page: MutableLive<LazyState<Page<T>>> = mutableLiveOf(Pending)

    override val continuous
        get() = buildList {
            forEachPage { page ->
                addAll(page.items.mapIndexed { index, row -> Row(index * page.number, row.item) })
            }
        }.toIList()

    override fun forEachPage(block: (Page<T>) -> Unit) {
        var i = 1
        var page = cache.load(capacity, i)
        while (page != null) {
            block(page)
            i++
            page = cache.load(capacity, i)
        }
    }

    override fun readPageFromMemoryOrEmpty(page: Int, cap: Int): Page<T> = cache.load(capacity, page) ?: Page(capacity = capacity, number = page)

    override fun wipeMemory() = cache.clear()

    override fun clearPages() {
        wipeMemory()
        page.value = Pending
    }

    override fun updateLoader(loader: (no: Int, capacity: Int) -> Later<Page<@UnsafeVariance T>>) {
        this.loader = loader
    }

    override fun setPageCapacity(cap: Int) {
        capacity = cap
    }

    override fun loadNextPage() = when (val state = page.value) {
        is Pending -> loadPage(1)
        is Loading -> loadNothing()
        is Failure -> loadPage(1) // FailedLater(RESOLVE_ERROR)
        is Success -> when {
            state.data.isEmpty -> Later(state.data)
            state.data.items.size < state.data.capacity -> Later(state.data)
            else -> loadPage(state.data.number + 1)
        }
    }

    override fun loadPreviousPage() = when (val state = page.value) {
        is Pending -> loadPage(1)
        is Loading -> loadNothing()
        is Failure -> loadPage(1) // FailedLater(RESOLVE_ERROR)
        is Success -> when {
            state.data.number > 1 -> loadPage(state.data.number - 1)
            else -> loadPage(1)
        }
    }

    override fun loadPage(no: Int): Later<Page<T>> {
        if (page.value is Loading) return FailedLater(LOADING_ERROR)
        val memorizedPage = cache.load(capacity, no)
        page.value = Loading("Loading", memorizedPage)
        return try {
            loader(no, capacity)
        } catch (err: Throwable) {
            FailedLater(err)
        }.then {
            page.value = Success(cache.save(capacity, it))
            it
        }.catch {
            page.value = Failure(it, data = memorizedPage)
            throw it
        }
    }

    override fun refresh() = when (val state = page.value) {
        is Pending -> loadPage(1)
        is Loading -> loadNothing()
        is Failure -> loadPage(1)
        is Success -> loadPage(state.data.number)
    }

    private fun loadNothing() = Later(Unit)

    override fun loadFirstPage(): Later<Page<T>> = loadPage(1)

    override fun loadLastPage(): Later<Page<T>> = loadPage(-1)

    override fun findRow(page: Int, row: Int) = cache.load(capacity, page, row)

    companion object {
        val RESOLVE_ERROR = Throwable("Can't resolve page number while paginator is in a failure state")
        val LOADING_ERROR = Throwable("Can't load page while paginator is still loading")
    }
}