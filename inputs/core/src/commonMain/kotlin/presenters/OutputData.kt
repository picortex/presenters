@file:JsExport

package presenters

import kotlin.js.JsExport

interface OutputData<out D> {
    val output: D?
}