@file:JsExport

package presenters.fields

import kotlin.js.JsExport

interface FormattableData<out I, out O> : RawData<I, O> {
    val formatted: String
}