@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import kotlin.js.JsExport

interface TransformedInput<in I, O> {
    val formatter: Formatter<O>?
    val transformer: (I?) -> O?
}