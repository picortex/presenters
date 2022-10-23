package presenters.collections.internal

import koncurrent.Later
import koncurrent.later.catch
import kotlinx.collections.interoperable.toInteroperableList
import live.Live
import presenters.collections.Page
import presenters.collections.PageableState
import presenters.collections.PaginationManager
import presenters.collections.Row
import viewmodel.ViewModel
import viewmodel.ViewModelConfig

@PublishedApi
internal class PaginationManagerImpl<out T>(
    override var capacity: Int,
    private val ram: PageableRamInMemory<T> = PageableRamInMemory(),
    private var loader: (no: Int, capacity: Int) -> Later<Page<T>>
) : ViewModel<PageableState<T>>(ViewModelConfig().of(PageableState.UnLoaded(ram.readOrNull(1, capacity)))), PaginationManager<T> {

    override val live: Live<PageableState<T>> get() = ui

    override val continuous
        get() = buildList {
            forEachPage { page ->
                addAll(page.items.mapIndexed { index, row -> Row(index * page.number, row.item) })
            }
        }.toInteroperableList()

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
        ui.value = PageableState.UnLoaded(null)
    }

    override fun updateLoader(loader: (no: Int, capacity: Int) -> Later<Page<@UnsafeVariance T>>) {
        this.loader = loader
    }

    override fun setPageCapacity(cap: Int) {
        capacity = cap
    }

    override fun loadNextPage() = when (val state = ui.value) {
        is PageableState.UnLoaded -> loadPage(1)
        is PageableState.Loading -> Later.reject(LOADING_ERROR)
        is PageableState.Failure -> loadPage(1) // Later.reject(RESOLVE_ERROR)
        is PageableState.LoadedPage -> when {
            state.page.isEmpty -> Later.resolve(state.page)
            state.page.items.size < state.page.capacity -> Later.resolve(state.page)
            else -> loadPage(state.page.number + 1)
        }
    }

    override fun loadPreviousPage(): Later<Page<T>> = when (val state = ui.value) {
        is PageableState.UnLoaded -> loadPage(1)
        is PageableState.Loading -> Later.reject(LOADING_ERROR)
        is PageableState.Failure -> loadPage(1) // Later.reject(RESOLVE_ERROR)
        is PageableState.LoadedPage -> when {
            state.page.number > 1 -> loadPage(state.page.number - 1)
            else -> loadPage(1)
        }
    }

    override fun loadPage(no: Int): Later<Page<T>> {
        if (ui.value is PageableState.Loading) return Later.reject(LOADING_ERROR)

        val memorizedPage = ram.readOrNull(no, capacity)
        ui.value = PageableState.Loading("Loading", memorizedPage)
        return loader(no, capacity).then {
            ram.write(it)
            ui.value = PageableState.LoadedPage(it)
            it
        }.catch {
            ui.value = PageableState.Failure(it, page = memorizedPage)
            throw it
        }
    }

    override fun refresh(): Later<Page<T>> = when (val state = ui.value) {
        is PageableState.UnLoaded -> loadPage(1)
        is PageableState.Loading -> Later.reject(LOADING_ERROR)
        is PageableState.Failure -> loadPage(1)
        is PageableState.LoadedPage -> loadPage(state.page.number)
    }

    override fun loadFirstPage(): Later<Page<T>> = loadPage(1)

    override fun loadLastPage(): Later<Page<T>> = loadPage(-1)

    companion object {
        val RESOLVE_ERROR = Throwable("Can't resolve page number while paginator is in a failure state")
        val LOADING_ERROR = Throwable("Can't load page while paginator is still loading")
    }
}