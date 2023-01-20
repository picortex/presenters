@file:JsExport

package presenters.fields

import presenters.OutputData
import kotlin.js.JsExport

interface FormattedData<out I, out O> : OutputData<O> {
    val input: I?
    val formatted: String
}