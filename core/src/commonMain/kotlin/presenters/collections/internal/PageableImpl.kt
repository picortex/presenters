package presenters.collections.internal

import presenters.collections.*
import viewmodel.ViewModel
import viewmodel.ViewModelConfig

abstract class PageableImpl<T>(config: ViewModelConfig) : ViewModel<PageableState<T>>(config.of(PageableState.UnLoaded())), Pageable<T>