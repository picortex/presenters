@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters

import kollections.List
import live.Live
import kotlin.js.JsExport

interface LiveOutputList<out D> : LiveOutputData<List<D>> {
    override val data: Live<OutputList<D>>
}