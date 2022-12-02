package changes

import expect.expect
import expect.toBe
import kash.Money
import kash.TZS
import kash.USD
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import presenters.changes.ChangeRemark
import presenters.changes.changeRemarkOf
import kotlin.test.Ignore
import kotlin.test.Test

@Ignore // Serializing classes that extend Nothing is a pain in the b**t
class ChangeRemarkSerialization {
    @Test
    fun should_serialize_primitives_decrease() {
        val change = changeRemarkOf(4, 3)
        expect(Json.encodeToString(change)).toBe("""{"pct":{"asDouble":25.0},"value":1.0,"decreased":true}""")
    }

    @Test
    fun should_deserialize_primitives_decrease() {
        val change = changeRemarkOf(4, 3)
        expect(Json.decodeFromString<ChangeRemark<Double>>("""{"pct":{"asDouble":25.0},"value":1.0,"decreased":true}""")).toBe(change)
    }

    @Test
    fun should_serialize_money_decrease() {
        val change = changeRemarkOf(4000.TZS, 3000.TZS)
        expect(Json.encodeToString(change)).toBe("""{"pct":{"asDouble":25.0},"value":{"amount":100000,"currency":"TZS"},"decreased":true}""")
    }

    @Test
    fun should_deserialize_money_decrease() {
        val change = changeRemarkOf(4000.TZS, 3000.TZS)
        expect(Json.decodeFromString<ChangeRemark<Money>>("""{"pct":{"asDouble":25.0},"value":{"amount":100000,"currency":"TZS"},"decreased":true}""")).toBe(change)
    }

    @Test
    fun should_serialize_fixed_money() {
        val change = changeRemarkOf(4000.TZS, 4000.TZS)
        expect(Json.encodeToString(change)).toBe("""{"at":{"amount":400000,"currency":"TZS"},"fixed":true}""")
    }

    @Test
    fun should_deserialize_fixed_money() {
        val change = changeRemarkOf(4000.TZS, 4000.TZS)
        expect(Json.decodeFromString<ChangeRemark<Money>>("""{"at":{"amount":400000,"currency":"TZS"},"fixed":true}""")).toBe(change)
    }

    @Test
    fun should_serialize_indeterminate_money() {
        val change = changeRemarkOf(4000.TZS, 4000.USD)
        expect(Json.encodeToString(change)).toBe("""{}""")
    }

    @Test
    fun should_deserialize_indeterminate_money() {
        val change = changeRemarkOf(4000.TZS, 4000.USD)
        println("Before decode")
        val decoded = Json.decodeFromString<ChangeRemark<Money>>("""{"me":false}""")
        println("After decode")
        expect(decoded).toBe<ChangeRemark.Indeterminate>()
        expect(decoded).toBe(change)
    }

    @Test
    fun should_serialize_fixed_numbers() {
        val change = changeRemarkOf(4000, 4000)
        expect(Json.encodeToString(change)).toBe("""{"at":4000.0,"fixed":true}""")
    }

    @Test
    fun should_deserialize_fixed_numbers() {
        val change = changeRemarkOf(4000, 4000)
        expect(Json.decodeFromString<ChangeRemark<Double>>("""{"at":4000.0,"fixed":true}""")).toBe(change)
    }
}