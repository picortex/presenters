package presenters.fields

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
@JsExport
data class Range<out T>(
    val start: T,
    val end: T
)