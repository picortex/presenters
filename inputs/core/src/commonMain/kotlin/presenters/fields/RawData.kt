@file:JsExport

package presenters.fields

import kotlin.js.JsExport

interface RawData<out I, out O> : OutputData<O> {
    val raw: I?
}