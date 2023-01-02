@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import kollections.List
import kotlin.js.JsExport

interface OutputList<out D> : OutputData<List<D>>, List<D> {
    override val output: List<D>
}