@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import live.Live
import kotlin.js.JsExport

interface FormattableInput<I, O> {
    val formatter: ((O?) -> I?)?
    val input: Live<InputData<I?>>
}