@file:JsExport

package presenters.containers

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
data class DetailBox<out T>(
    val value: T,
    val details: String
)