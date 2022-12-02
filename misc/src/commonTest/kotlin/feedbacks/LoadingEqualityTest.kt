package feedbacks

import expect.expect
import expect.toBeUnequalTo
import presenters.cases.Feedback
import kotlin.test.Test

class LoadingEqualityTest {
    @Test
    fun two_instances_with_equal_message_should_be_equal() {
        expect(Feedback.Loading("Loading")).toBe(Feedback.Loading("Loading"))
    }

    @Test
    fun different_feedbacks_with_same_message_should_not_be_the_same() {
        expect<Feedback>(Feedback.Success("test")).toBeUnequalTo(Feedback.Loading("test"))
    }
}