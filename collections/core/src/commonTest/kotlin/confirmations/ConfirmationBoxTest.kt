package confirmations

import expect.expect
import koncurrent.Later
import koncurrent.later.catch
import live.expect
import live.toHaveGoneThrough2
import presenters.confirmations.ConfirmationBox
import presenters.states.Failure
import presenters.states.Loading
import presenters.states.Pending
import presenters.states.Success
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
                Later.resolve(5)
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
                Later.resolve(confirmed)
            }
        }
        expect(confirmed).toBe(false)

        box.confirm()
        expect(box.state).toHaveGoneThrough2<Loading<*>, Success<*>>()
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
                Later.reject(RuntimeException("Rejecting for fun"))
            }
        }
        expect(cancelled).toBe(false)
        expect(confirmed).toBe(false)

        box.confirm()
        expect(box.state).toHaveGoneThrough2<Loading<*>, Failure<*>>()
        expect(cancelled).toBe(false)
        expect(confirmed).toBe(true)
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
        expect(box.state).toHaveGoneThrough2<Loading<*>, Failure<*>>()
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
        expect(box.state).toHaveGoneThrough2<Loading<*>, Failure<*>>()
        expect(caught).toBe(true)
    }
}