@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters

import live.Live
import kollections.List
import kotlin.js.JsExport

interface LiveDataList<out D> : LiveData<List<D>> {
    override val data: Live<DataList<D>>
}