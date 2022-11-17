package presenters.collections.internal

import kollections.toIList
import koncurrent.Later
import koncurrent.later.catch
import live.MutableLive
import live.mutableLiveOf
import presenters.collections.Page
import presenters.collections.PaginationManager
import presenters.collections.Row
import presenters.states.Failure
import presenters.states.LazyState
import presenters.states.Loading
import presenters.states.Pending
import presenters.states.Success

@PublishedApi
internal class PaginationManagerImpl<out T>(
    override var capacity: Int,
    private val ram: PageableRamInMemory<T> = PageableRamInMemory(),
    private var loader: (no: Int, capacity: Int) -> Later<Page<T>>
) : PaginationManager<T> {

    override val page: MutableLive<LazyState<Page<@UnsafeVariance T>>> = mutableLiveOf(Pending)

    override val continuous
        get() = buildList {
            forEachPage { page ->
                addAll(page.items.mapIndexed { index, row -> Row(index * page.number, row.item) })
            }
        }.toIList()

    override fun forEachPage(block: (Page<T>) -> Unit) {
        var i = 1
        var page = ram.readOrNull(i, capacity)
        while (page != null) {
            block(page)
            i++
            page = ram.readOrNull(i, capacity)
        }
    }

    override fun readPageFromMemory(page: Int, cap: Int): Page<T> = ram.read(page, capacity)

    override fun readPageFromMemoryOrNull(page: Int, cap: Int): Page<T>? = ram.readOrNull(page, capacity)

    override fun writePageToMemory(page: Page<@UnsafeVariance T>): Page<T> = ram.write(page)

    override fun wipeMemory() = ram.wipe()

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
        is Failure -> loadPage(1) // Later.reject(RESOLVE_ERROR)
        is Success -> when {
            state.data.isEmpty -> Later.resolve(state.data)
            state.data.items.size < state.data.capacity -> Later.resolve(state.data)
            else -> loadPage(state.data.number + 1)
        }
    }

    override fun loadPreviousPage() = when (val state = page.value) {
        is Pending -> loadPage(1)
        is Loading -> loadNothing()
        is Failure -> loadPage(1) // Later.reject(RESOLVE_ERROR)
        is Success -> when {
            state.data.number > 1 -> loadPage(state.data.number - 1)
            else -> loadPage(1)
        }
    }

    override fun loadPage(no: Int): Later<Page<T>> {
        if (page.value is Loading) return Later.reject(LOADING_ERROR)
        val memorizedPage = ram.readOrNull(no, capacity)
        page.value = Loading("Loading", memorizedPage)
        return try {
            loader(no, capacity)
        } catch (err: Throwable) {
            Later.reject(err)
        }.then {
            ram.write(it)
            page.value = Success(it)
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

    private fun loadNothing() = Later.resolve(Unit)

    override fun loadFirstPage(): Later<Page<T>> = loadPage(1)

    override fun loadLastPage(): Later<Page<T>> = loadPage(-1)

    companion object {
        val RESOLVE_ERROR = Throwable("Can't resolve page number while paginator is in a failure state")
        val LOADING_ERROR = Throwable("Can't load page while paginator is still loading")
    }
}