@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters

import kotlinx.serialization.KSerializer
import live.Live
import kotlin.js.JsExport

interface LiveOutputData<out D> : InputField {
    val data: Live<OutputData<D>>
    val serializer: KSerializer<@UnsafeVariance D>
}