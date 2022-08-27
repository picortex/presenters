@file:Suppress("WRONG_EXPORTED_DECLARATION", "NON_EXPORTABLE_TYPE")

package presenters.forms

import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import viewmodel.ViewModelConfig
import kotlin.js.JsExport
import kotlin.jvm.JvmField

@JsExport
interface FormConfig<P> : ViewModelConfig {
    val codec: StringFormat
    val serializer: KSerializer<P>
    val exitOnSubmitted: Boolean

    companion object {
        @JvmField
        val DEFAULT_EXIT_ON_SUBMITTED = true
    }
}