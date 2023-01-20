package fields.text

import expect.expect
import expect.toBe
import kotlinx.coroutines.test.runTest
import presenters.TextInputField
import presenters.validation.Invalid
import presenters.validation.Valid
import kotlin.test.Test

class TextInputFieldValidationTest {

    @Test
    fun should_pass_validation_if_it_the_field_is_not_required_and_input_has_not_been_set() {
        val name = TextInputField(name = "Test")
        name.validate()
        expect(name.data.value.output).toBe(null)
    }

    @Test
    fun should_fail_validation_if_the_input_is_required_and_no_input_has_been_provided() = runTest {
        val name = TextInputField(name = "Test", isRequired = true)
        expect(name.data.value.output).toBe(null)
        val res = expect(name.validate()).toBe<Invalid>()
        expect(res.cause.message).toBe("Test is required")
        expect(name.data.value.output).toBe(null)
    }

    @Test
    fun should_pass_validation_if_max_length_has_not_been_set() = runTest {
        val name = TextInputField(name = "test", label = "Test")
        name.type("Anderson")
        expect(name.validate()).toBe<Valid>()
        expect(name.data.value.output).toBe("Anderson")
    }

    @Test
    fun should_pass_validation_if_max_length_has_been_set_and_provided_input_does_not_exceed_it() = runTest {
        val name = TextInputField(name = "test", label = "Test", maxLength = 20)
        name.type("Anderson")
        expect(name.validate()).toBe<Valid>()
        expect(name.data.value.output).toBe("Anderson")
    }

    @Test
    fun should_fail_validation_if_min_length_has_been_set_and_violated() = runTest {
        val name = TextInputField(name = "test", label = "Test", minLength = 20)
        name.type("Anderson")
        val res = expect(name.validate()).toBe<Invalid>()
        expect(res.cause.message).toBe("Test must have more than 20 characters")
    }

    @Test
    fun should_pass_validation_if_min_length_has_not_been_set() = runTest {
        val name = TextInputField(name = "test", label = "Test")
        name.type("Anderson")
        expect(name.validate()).toBe<Valid>()
        expect(name.data.value.output).toBe("Anderson")
    }

    @Test
    fun should_pass_validation_if_min_length_has_been_set_and_provided_input_does_not_violate_it() = runTest {
        val name = TextInputField(name = "test", label = "Test", minLength = 5)
        name.type("Anderson")
        expect(name.validate()).toBe<Valid>()
        expect(name.data.value.output).toBe("Anderson")
    }

    @Test
    fun should_fail_validation_if_min_length_has_been_set_and_provided_input_violates_it() = runTest {
        val name = TextInputField(name = "test", label = "Test", minLength = 30)
        name.type("Anderson")
        val res = expect(name.validate()).toBe<Invalid>()
        expect(res.cause.message).toBe("Test must have more than 30 characters")
    }

    @Test
    fun should_pass_validation_if_all_criteria_have_been_met() {
        val name = TextInputField(name = "test", label = "Test", minLength = 3, maxLength = 10)
        name.type("Anderson")
        expect(name.validate()).toBe<Valid>()
        expect(name.data.value.output).toBe("Anderson")
    }

    @Test
    fun should_fail_validation_if_input_is_required_and_provided_value_is_blank() = runTest {
        val name = TextInputField(name = "test", label = "Test", isRequired = true)
        name.set("")
        val res = expect(name.validate()).toBe<Invalid>()
        expect(res.cause.message).toBe("Test is required")
        expect(name.data.value.output).toBe("")
    }

    @Test
    fun should_add_custom_validation_to_the_mix() = runTest {
        val name = TextInputField(
            name = "test", label = "Test", isRequired = true,
            validator = { if (it == "Anderson") throw IllegalArgumentException("Bad name Anderson") }
        )
        name.type("Anderson")
        val res = expect(name.validate()).toBe<Invalid>()
        expect(res.cause.message).toBe("Bad name Anderson")
        expect(name.data.value.output).toBe("Anderson")
    }
}