package fields.email

import expect.expect
import expect.expectFailure
import kotlinx.coroutines.test.runTest
import presenters.fields.EmailInputField
import kotlin.test.Test

class EmailInputValidationTest {
    @Test
    fun should_fail_validation_if_input_is_not_a_valid_email() = runTest {
        val email = EmailInputField("email")
        email.value = "andy"
        val exp = expectFailure { email.validate() }
        expect(exp.message).toBe("Invalid email: andy")
    }

    @Test
    fun should_pass_validation_if_input_is_a_valid_email() = runTest {
        val email = EmailInputField("email")
        email.value = "andy@lamax.com"
        email.validate()
        expect(email.value).toBe("andy@lamax.com")
    }
}