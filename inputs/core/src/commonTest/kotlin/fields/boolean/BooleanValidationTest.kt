package fields.boolean

import expect.expect
import expect.toBe
import kotlinx.coroutines.test.runTest
import presenters.fields.BooleanInputField
import presenters.fields.Invalid
import presenters.fields.Valid
import kotlin.test.Test

class BooleanValidationTest {
    @Test
    fun should_fail_validation_if_input_is_required() = runTest {
        val switch = BooleanInputField(name = "switch", isRequired = true)
        val res = switch.validate() as Invalid
        expect(res.cause.message).toBe("Switch is required")
    }

    @Test
    fun should_pass_validation_if_input_is_set() = runTest {
        val switch = BooleanInputField(name = "switch")
        switch.set(true)
        val res = switch.validate()
        expect(res).toBe<Valid>()
    }
}