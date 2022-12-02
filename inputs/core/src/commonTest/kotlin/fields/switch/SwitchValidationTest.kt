package fields.switch

import expect.expect
import expect.expectFailure
import kotlinx.coroutines.test.runTest
import presenters.fields.SwitchInputField
import kotlin.test.Test

class SwitchValidationTest {
    @Test
    fun should_fail_validation_if_input_is_required() = runTest {
        val switch = SwitchInputField(name = "switch", isRequired = true)
        val exp = expectFailure { switch.validate() }
        expect(exp.message).toBe("Switch is required")
    }

    @Test
    fun should_pass_validation_if_input_is_set() = runTest {
        val switch = SwitchInputField(name = "switch")
        switch.value = true
        switch.validate()
    }
}