package fields.numbers

import expect.expect
import presenters.fields.DoubleValuedField
import presenters.fields.Formatter
import kotlin.test.Test

class NumberInputFormatterTest {
    @Test
    fun should_be_able_to_leave_numbers_as_they_are_when_the_applied_format_has_nothing_to_do() {
        val number = DoubleValuedField(name = "price")
        number.type("100")
        with(number.data.value) {
            expect(raw).toBe("100")
            expect(formatted).toBe("100")
            expect(output).toBe(100.0)
        }
    }

    @Test
    fun should_be_able_to_format_thousands_as_they_are_typed_in() {
        val number = DoubleValuedField(
            name = "price",
            formatter = { it?.toInt()?.toString()?.toCharArray()?.joinToString(",") }
        )
        number.type("1000")
        with(number.data.value) {
            expect(raw).toBe("1000")
            expect(formatted).toBe("1,0,0,0")
            expect(output).toBe(1000.0)
        }

        number.type("0")
        with(number.data.value) {
            expect(raw).toBe("10000")
            expect(formatted).toBe("1,0,0,0,0")
            expect(output).toBe(10_000.0)
        }
    }
}