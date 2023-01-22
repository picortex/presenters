@file:JsExport

package presenters

import kotlin.js.JsExport

interface Data<out D> {
    val output: D?
}