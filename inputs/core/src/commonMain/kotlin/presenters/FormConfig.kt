@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters

import kotlinx.serialization.KSerializer
import viewmodel.ScopeConfig
import kotlin.js.JsExport

interface FormConfig<P> : ScopeConfig<Unit> {
    val serializer: KSerializer<P>
    val exitOnSubmitted: Boolean
}