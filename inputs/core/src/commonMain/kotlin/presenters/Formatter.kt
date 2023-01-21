@file:JsExport

package presenters

import kotlin.js.JsExport

fun interface Formatter<in O> {
    operator fun invoke(value: O?): String?
}