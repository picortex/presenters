@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.cards

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
data class ValueCard<out T>(
    val title: String,
    val value: T,
    val details: String,
    val priority: Int = -1
)