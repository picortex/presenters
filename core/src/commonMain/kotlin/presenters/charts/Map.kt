@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.charts

import kotlinx.collections.interoperable.List
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
data class Map(
    val name: String,
    val details: String,
    val coordinates: List<Location>
) {

    @Serializable
    data class Location(
        val address: String,
        val latitude: Double,
        val longitude: Double
    )
}