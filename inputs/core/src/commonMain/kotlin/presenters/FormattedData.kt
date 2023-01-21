@file:JsExport

package presenters

import kotlin.js.JsExport

interface FormattedData<out I, out O> : OutputData<O> {
    val input: I?
    val formatted: String
}