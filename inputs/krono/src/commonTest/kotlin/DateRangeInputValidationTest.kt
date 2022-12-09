import expect.expect
import expect.toBe
import krono.LocalDate
import presenters.fields.DateRangeInputField
import presenters.fields.InputFieldState
import presenters.fields.Range
import kotlin.test.Test

class DateRangeInputValidationTest {
    @Test
    fun should_fail_to_submit_with_partial_data() {
        val dr = DateRangeInputField("span")
        dr.setStart("2022-01-20")

        expect(dr.output.value).toBe(null)
        val exp = expect(dr.feedback.value).toBe<InputFieldState.Warning>()
        expect(exp.message).toBe("Span end is required")
    }

    @Test
    fun should_submit_if_all_start_and_end_dates_are_provided() {
        val dr = DateRangeInputField("span")
        dr.setStart("2022-01-20")
        println(dr.start.output.value)
        println(dr.output.value)
        dr.setEnd("2022-01-30")
        println(dr.end.output.value)
        println(dr.output.value)

        val actual = Range(
            start = LocalDate(2022, 1, 20),
            end = LocalDate(2022, 1, 30)
        )
        expect(dr.output.value).toBe(actual)
        expect(dr.feedback.value).toBe<InputFieldState.Empty>()
    }

    @Test
    fun should_return_null_if_start_and_end_dates_are_flipped() {
        val dr = DateRangeInputField("span")
        dr.setStart("2022-01-30")
        dr.setEnd("2022-01-20")

        expect(dr.output.value).toBe(null)
        val exp = expect(dr.feedback.value).toBe<InputFieldState.Warning>()
        expect(exp.message).toBe("Span can't range from 2022-01-30 to 2022-01-20")
    }
}