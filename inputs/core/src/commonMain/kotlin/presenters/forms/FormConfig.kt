@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.forms

import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import viewmodel.ViewModelConfig
import kotlin.js.JsExport
import kotlin.jvm.JvmField

interface FormConfig<P> : ViewModelConfig {
    val serializer: KSerializer<P>
    val exitOnSubmitted: Boolean
}