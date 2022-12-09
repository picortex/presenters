package presenters.internal

import geo.Country
import geo.GeoLocation
import kollections.toIList
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class GooglePlacesApiParser {
    private val codex = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    fun parseOrNull(json: String?): GeoLocation? = try {
        parse(json!!)
    } catch (err: Throwable) {
        null
    }

    fun parse(json: String): GeoLocation {
        val resp = codex.decodeFromString<GooglePlaceApiResponse>(json)

        val postalCode = resp.address_components.firstOrNull {
            it.types.contains("postal_code")
        }
        val countryKey = resp.address_components.firstOrNull {
            it.types.contains("country")
        } ?: throw IllegalArgumentException("Failed to match a country type in address_component")

        val country = Country.values().firstOrNull {
            it.label.contains(countryKey.long_name, ignoreCase = true) || countryKey.short_name == it.name
        } ?: throw IllegalArgumentException("Failed to parse country ${countryKey.long_name}(${countryKey.short_name})")

        return GeoLocation(
            lines = resp.description.split(", ").toIList(),
            country = country,
            cords = resp.geometry.location,
            code = postalCode?.long_name
        )
    }
}