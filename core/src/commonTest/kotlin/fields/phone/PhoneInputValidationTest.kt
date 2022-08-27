package fields.phone

import expect.expect
import expect.expectFailure
import kotlinx.coroutines.test.runTest
import presenters.fields.PhoneInputField
import kotlin.test.Test

class PhoneInputValidationTest {
    @Test
    fun should_fail_validation_if_input_is_not_a_valid_phone() = runTest {
        val phone = PhoneInputField("phone")
        phone.value = "0"
        val exp = expectFailure { phone.validate() }
        expect(exp.message).toBe("Invalid phone number ")
    }

    @Test
    fun should_pass_validation_if_input_is_a_valid_phone() = runTest {
        val phone = PhoneInputField("phone")
        phone.value = "0752748674"
        phone.validate()
        expect(phone.value).toBe("0752748674")
    }
}