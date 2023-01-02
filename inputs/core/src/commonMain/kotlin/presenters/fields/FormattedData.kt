@file:JsExport

package presenters.fields

import kotlin.js.JsExport

interface FormattedData<out I, out O> : RawData<I, O> {
    val formatted: String
}