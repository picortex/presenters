package presenters.internal

import geo.LatLng
import kotlinx.serialization.Serializable

@Serializable
internal class GooglePlaceApiResponse(
    val address_components: List<AddressComponent>,
    val description: String,
    val geometry: Geometry
) {
    @Serializable
    class Geometry(
        val location: LatLng
    )

    @Serializable
    class AddressComponent(
        val long_name: String,
        val short_name: String,
        val types: List<String>
    )
}