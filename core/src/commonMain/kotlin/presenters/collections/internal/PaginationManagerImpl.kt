package presenters.collections.internal

import koncurrent.Fulfilled
import koncurrent.Later
import koncurrent.Rejected
import koncurrent.later.finally
import live.Live
import presenters.collections.Page
import presenters.collections.PageableState
import presenters.collections.PaginationManager
import viewmodel.ViewModel
import viewmodel.ViewModelConfig

@PublishedApi
internal class PaginationManagerImpl<out T>(
    override var capacity: Int,
    private val ram: PageableRamInMemory<T> = PageableRamInMemory(),
    private val onPage: (no: Int, capacity: Int) -> Later<Page<T>>
) : ViewModel<PageableState<T>>(ViewModelConfig().of(PageableState.UnLoaded(ram.readOrNull(1, capacity)))), PaginationManager<T> {

    override val live: Live<PageableState<T>> get() = ui

    override fun readPageFromMemory(page: Int, cap: Int): Page<T> = ram.read(page, capacity)

    override fun readPageFromMemoryOrNull(page: Int, cap: Int): Page<T>? = ram.readOrNull(page, capacity)

    override fun writePageToMemory(page: Page<@UnsafeVariance T>): Page<T> = ram.write(page)

    override fun setPageCapacity(cap: Int) {
        capacity = cap
    }

    override fun loadNextPage() = when (val state = ui.value) {
        is PageableState.Failure -> Later.reject(Throwable("Can't load next page because paginator is in a failure state"))
        is PageableState.LoadedPage -> when {
            state.page.isEmpty -> Later.resolve(state.page)
            state.page.items.size < state.page.capacity -> Later.resolve(state.page)
            else -> loadPage(state.page.number + 1)
        }

        is PageableState.Loading -> Later.reject(Throwable("Can't load next page because paginator is still loading"))
        is PageableState.UnLoaded -> loadPage(1)
    }

    override fun loadPreviousPage(): Later<Page<T>> = when (val state = ui.value) {
        is PageableState.Failure -> Later.reject(Throwable("Can't load next page because paginator is in a failure state"))
        is PageableState.LoadedPage -> when {
            state.page.number > 1 -> loadPage(state.page.number - 1)
            else -> Later.resolve(state.page)
        }

        is PageableState.Loading -> Later.reject(Throwable("Can't load next page because paginator is still loading"))
        is PageableState.UnLoaded -> loadPage(1)
    }

    override fun loadPage(no: Int): Later<Page<T>> {
        val memorizedPage = ram.readOrNull(no, capacity)
        ui.value = PageableState.Loading("Loading", memorizedPage)
        return onPage(no, capacity).finally {
            when (it) {
                is Fulfilled -> {
                    ram.write(it.value)
                    ui.value = PageableState.LoadedPage(it.value)
                }

                is Rejected -> ui.value = PageableState.Failure(it.cause, page = memorizedPage)
            }
        }
    }

    override fun refresh(): Later<Page<T>> = when (val state = ui.value) {
        is PageableState.Failure -> loadPage(1)
        is PageableState.LoadedPage -> loadPage(state.page.number)
        is PageableState.Loading -> Later.reject(Throwable("Can't load next page because paginator is still loading"))
        is PageableState.UnLoaded -> loadPage(1)
    }

    override fun loadFirstPage(): Later<Page<T>> = loadPage(1)

    override fun loadLastPage(): Later<Page<T>> = loadPage(-1)
}