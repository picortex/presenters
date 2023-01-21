@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters

import live.Live
import kotlin.js.JsExport

interface LiveFormattedData<I, out O> : LiveOutputData<O> {
    override val data: Live<FormattedData<I, O>>
}