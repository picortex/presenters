@file:JsExport

package presenters

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
data class Range<out T>(
    val start: T,
    val end: T
) {
    companion object {
        fun <T : Any> of(start: T?, end: T?): Range<T>? = if (start != null && end != null) Range(start, end) else null
    }
}