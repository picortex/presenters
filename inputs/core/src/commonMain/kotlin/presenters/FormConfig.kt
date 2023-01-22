@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters

import kotlinx.serialization.KSerializer
import viewmodel.ViewModelConfig
import kotlin.js.JsExport

interface FormConfig<P> : ViewModelConfig {
    val serializer: KSerializer<P>
    val exitOnSubmitted: Boolean
}