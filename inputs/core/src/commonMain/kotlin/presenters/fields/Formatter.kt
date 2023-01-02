@file:JsExport

package presenters.fields

import kotlin.js.JsExport

fun interface Formatter<in O> {
    operator fun invoke(value: O?): String?
}