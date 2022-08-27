package presenters.fields

import kotlin.js.JsExport

@JsExport
data class Range<out T>(
    val start: T,
    val end: T
)