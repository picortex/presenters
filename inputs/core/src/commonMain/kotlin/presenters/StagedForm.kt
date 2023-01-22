@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters

import kase.FormState
import kase.Pending
import presenters.collections.*
import viewmodel.ViewModel
import kotlin.js.JsExport

open class StagedForm<out P, out R>(
    open val heading: String,
    open val details: String,
    open val config: FormConfig<@UnsafeVariance P>,
    initializer: FormActionsBuildingBlock<P, R>,
) : ViewModel<FormState<R>>(config.of(Pending)) {

}