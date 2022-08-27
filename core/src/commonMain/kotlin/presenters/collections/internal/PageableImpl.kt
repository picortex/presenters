@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections.internal

import presenters.collections.*
import viewmodel.ViewModel
import viewmodel.ViewModelConfig
import kotlin.js.JsExport

abstract class PageableImpl<T>(config: ViewModelConfig) : ViewModel<PageableState<T>>(config.of(PageableState.UnLoaded())), Pageable<T>