package fields.text

import expect.expect
import live.expect
import live.toHaveGoneThrough3
import live.toHaveGoneThrough5
import live.toHaveGoneThrough6
import presenters.fields.InputFieldState.Empty
import presenters.fields.InputFieldState.Warning
import presenters.fields.TextInputField
import kotlin.test.Test

class TextInputFeedbackTest {
    @Test
    fun should_warn_without_throwing_while_typing_input() {
        val field = TextInputField(name = "Test", minLength = 3, maxLength = 4)
        field.type("Anders")
        val (w1, w2, _, _, w5, w6) = expect(field.feedback).toHaveGoneThrough6<Warning, Warning, Empty, Empty, Warning, Warning>()
        listOf(w1, w2).forEach {
            expect(it.message).toBe("Test must have more than 3 characters")
        }

        listOf(w5, w6).forEach {
            expect(it.message).toBe("Test must have less than 4 characters")
        }
    }
}