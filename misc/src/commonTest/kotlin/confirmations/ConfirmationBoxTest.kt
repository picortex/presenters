package confirmations

import expect.expect
import kase.Executing
import kase.Failure
import kase.Pending
import kase.Success
import koncurrent.FailedLater
import koncurrent.Later
import live.expect
import live.toHaveGoneThrough2
import presenters.confirmations.ConfirmationBox
import viewmodel.ScopeConfig
import kotlin.test.Test

class ConfirmationBoxTest {

    @Test
    fun a_confirmation_box_should_start_in_a_pending_state() {
        val box = ConfirmationBox(
            heading = "Delete George",
            details = "Are you sure you want to delete George?",
            config = ScopeConfig(Unit)
        ) {
            onConfirm {
                Later(5)
            }
        }
        expect(box.state).toBeIn(Pending)
    }

    @Test
    fun a_confirmation_box_can_be_driven_to_a_successful_execution() {
        var confirmed = false

        val box = ConfirmationBox(
            heading = "Delete George",
            details = "Are you sure you want to delete George?",
            config = ScopeConfig(Unit)
        ) {
            onConfirm {
                confirmed = true
                Later(confirmed)
            }
        }
        expect(confirmed).toBe(false)

        box.confirm()
        expect(box.state).toHaveGoneThrough2<Executing, Success<Any?>>()
        expect(confirmed).toBe(true)
    }

    @Test
    fun a_confirmation_box_can_be_driven_to_a_failed_execution() {
        var cancelled = false
        var confirmed = false

        val box = ConfirmationBox(
            heading = "Delete George",
            details = "Are you sure you want to delete George?",
            config = ScopeConfig(Unit)
        ) {
            onCancel {
                cancelled = true
            }
            onConfirm {
                confirmed = true
                FailedLater(RuntimeException("Rejecting for fun"))
            }
        }
        expect(cancelled).toBe(false)
        expect(confirmed).toBe(false)

        box.confirm()
        expect(box.state).toHaveGoneThrough2<Executing, Failure<Any?>>()
        expect(cancelled).toBe(false)
        expect(confirmed).toBe(true)
    }

    @Test
    fun a_confirmation_box_can_be_cancelled() {
        var cancelled = false

        val box = ConfirmationBox(
            heading = "Delete George",
            details = "Are you sure you want to delete George?",
            config = ScopeConfig(Unit)
        ) {
            onCancel {
                cancelled = true
            }
            onConfirm {
                FailedLater(RuntimeException("Rejecting for fun"))
            }
        }
        expect(cancelled).toBe(false)
        box.cancel()
        expect(cancelled).toBe(true)
    }

    @Test
    fun a_confirmation_box_can_be_driven_to_a_failed_execution_with_a_submit_function_that_can_throw() {
        var cancelled = false
        var confirmed = false

        val box = ConfirmationBox(
            heading = "Delete George",
            details = "Are you sure you want to delete George?",
            config = ScopeConfig(Unit)
        ) {
            onCancel {
                cancelled = true
            }
            onConfirm {
                confirmed = true
                throw RuntimeException("Rejecting for fun")
            }
        }
        expect(cancelled).toBe(false)
        expect(confirmed).toBe(false)

        box.confirm()
        expect(box.state).toHaveGoneThrough2<Executing, Failure<Any?>>()
        expect(cancelled).toBe(false)
        expect(confirmed).toBe(true)
    }

    @Test
    fun a_confirm_action_can_execute_the_catch_clause_of_a_later_after_a_failure() {
        val box = ConfirmationBox(
            heading = "Delete George",
            details = "Are you sure you want to delete George?",
            config = ScopeConfig(Unit)
        ) {
            onConfirm {
                throw RuntimeException("Rejecting for fun")
            }
        }

        var caught = false
        box.confirm().catch {
            caught = true
            println("Error: ${it.message}")
        }
        println("after catching")
        expect(box.state).toHaveGoneThrough2<Executing, Failure<Any?>>()
        expect(caught).toBe(true)
    }
}