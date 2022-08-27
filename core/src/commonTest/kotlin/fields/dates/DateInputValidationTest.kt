package fields.dates

import expect.expect
import expect.toBe
import krono.LocalDate
import presenters.fields.DateInputField
import presenters.fields.InputFieldState
import kotlin.test.Test

class DateInputValidationTest {
    @Test
    fun should_have_null_value_on_an_invalid_iso() {
        val dob = DateInputField("dob", "Date of Birth")
        dob.isoString = "2020"
        expect(dob.value).toBe(null)
        val feedback = expect(dob.feedback.value).toBe<InputFieldState.Warning>()
        expect(feedback.message).toBe("Invalid date 2020")
    }

    @Test
    fun should_have_a_value_when_assigned_a_correct_iso_string() {
        val dob = DateInputField("dob", "Date of Birth")
        dob.isoString = "2020-05-18"
        expect(dob.value).toBe(LocalDate(2020, 5, 18))
        expect(dob.feedback.value).toBe<InputFieldState.Empty>()

        dob.isoString = "2020-5-18"
        expect(dob.value).toBe(LocalDate(2020, 5, 18))
        expect(dob.feedback.value).toBe<InputFieldState.Empty>()
    }

    @Test
    fun should_have_a_waring_if_date_exceeds_max_date() {
        val dob = DateInputField("dob", "Date of Birth", maxDate = LocalDate(2022, 1, 1))
        dob.isoString = "2023-05-18"
        expect(dob.value).toBe(LocalDate(2023, 5, 18))
        val warning = expect(dob.feedback.value).toBe<InputFieldState.Warning>()
        expect(warning.message).toBe("Date of Birth must be before Jan 1, 2022")
    }
}