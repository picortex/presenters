package fields.email

import expect.expect
import expect.expectFailure
import expect.toBe
import kotlinx.coroutines.test.runTest
import presenters.fields.EmailInputField
import presenters.fields.Invalid
import presenters.fields.Valid
import kotlin.test.Test

class EmailInputValidationTest {
    @Test
    fun should_fail_validation_if_input_is_not_a_valid_email() = runTest {
        val email = EmailInputField("email")
        email.type("andy")
        val res = expect(email.validate()).toBe<Invalid>()
        expect(res.cause.message).toBe("Invalid email: andy")
    }

    @Test
    fun should_pass_validation_if_input_is_a_valid_email() = runTest {
        val email = EmailInputField("email")
        email.type("andy@lamax.com")
        expect(email.validate()).toBe<Valid>()
        expect(email.output.value).toBe("andy@lamax.com")
    }
}