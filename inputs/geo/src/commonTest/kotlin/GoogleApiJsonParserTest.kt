import expect.expect
import geo.Country
import presenters.internal.GooglePlacesApiParser
import kotlin.test.Test

class GoogleApiJsonParserTest {

    @Test
    fun should_be_able_to_parse_the_address_properly() {
        val parser = GooglePlacesApiParser()
        val resp = parser.parse(googlePlacesJson)
        expect(resp.address).toBe("Ubungo Terminal Rd, Dar es Salaam, Tanzania")
    }

    @Test
    fun should_be_able_to_parse_the_location_properly() {
        val parser = GooglePlacesApiParser()
        val resp = parser.parse(googlePlacesJson)
        expect(resp.cords?.lat).toBe(-6.7849335)
        expect(resp.cords?.lng).toBe(39.2188214)
    }

    @Test
    fun should_parse_a_google_api_json() {
        val parser = GooglePlacesApiParser()
        val resp = parser.parse(googlePlacesJson)
        expect(resp.address).toBe("Ubungo Terminal Rd, Dar es Salaam, Tanzania")

        expect(resp.country).toBe(Country.TZ)

        expect(resp.cords?.lat).toBe(-6.7849335)
        expect(resp.cords?.lng).toBe(39.2188214)
    }
}