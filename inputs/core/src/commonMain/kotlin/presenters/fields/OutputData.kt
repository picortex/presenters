@file:JsExport

package presenters.fields

import kotlin.js.JsExport

interface OutputData<out D> {
    val output: D?
}