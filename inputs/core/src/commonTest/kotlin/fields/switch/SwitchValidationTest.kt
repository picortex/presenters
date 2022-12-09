package fields.switch

import expect.expect
import expect.expectFailure
import expect.toBe
import kotlinx.coroutines.test.runTest
import presenters.fields.Invalid
import presenters.fields.SwitchInputField
import presenters.fields.Valid
import kotlin.test.Test
import kotlin.test.fail

class SwitchValidationTest {
    @Test
    fun should_fail_validation_if_input_is_required() = runTest {
        val switch = SwitchInputField(name = "switch", isRequired = true)
        val res = switch.validate() as Invalid
        expect(res.cause.message).toBe("Switch is required")
    }

    @Test
    fun should_pass_validation_if_input_is_set() = runTest {
        val switch = SwitchInputField(name = "switch")
        switch.field.value = true
        val res = switch.validate()
        expect(res).toBe<Valid>()
    }
}