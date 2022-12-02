import expect.expect
import kotlinx.datetime.*
import presenters.date.format
import kotlin.test.Test

class DateFormatterTest {
    @Test
    fun should_be_able_to_format_dates() {
        val now = LocalDate(year = 2022, month = Month.MARCH, dayOfMonth = 23)
        expect(now.format("{DD}/{MM}/{YY}")).toBe("23/03/22")
        expect(now.format("{DDD} of {DD} {MMMM} {YYYY}")).toBe("Wed of 23 March 2022")
        expect(now.format("{DDD} of {DD}{th} {MMMM} {YYYY}")).toBe("Wed of 23rd March 2022")
    }
}