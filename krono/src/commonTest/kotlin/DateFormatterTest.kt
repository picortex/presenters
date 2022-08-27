import expect.expect
import kotlinx.datetime.*
import presenters.date.DateFormatter
import kotlin.test.Test

class DateFormatterTest {
    @Test
    fun should_be_able_to_format_dates() {
        val now = LocalDate(year = 2022, month = Month.MARCH, dayOfMonth = 23)
        val formatter1 = DateFormatter("{DD}/{MM}/{YY}")
        expect(formatter1.format(now)).toBe("23/03/22")

        val formatter2 = DateFormatter("{DDD} of {DD} {MMMM} {YYYY}")
        expect(formatter2.format(now)).toBe("Wed of 23 March 2022")

        val formatter3 = DateFormatter("{DDD} of {DD}{th} {MMMM} {YYYY}")
        expect(formatter3.format(now)).toBe("Wed of 23rd March 2022")
    }
}