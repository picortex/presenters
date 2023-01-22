@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters

import live.Live
import kotlin.js.JsExport

interface LiveData<out D> {
    val data: Live<Data<D>>
}