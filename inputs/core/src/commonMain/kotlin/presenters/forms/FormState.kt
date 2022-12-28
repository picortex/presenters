@file:JsExport

package presenters.forms

import presenters.states.Failure.Companion.DEFAULT_MESSAGE as DEFAULT_FAILURE_MESSAGE
import kotlin.js.JsExport

@Deprecated("in favour of kase.FormState")
sealed class FormState {
    object Fillable : FormState()
    object Validating : FormState()

    data class Submitting(val json: String) : FormState() {
        override fun toString(): String = "Submitting $json"
    }

    object Submitted : FormState()

    data class Failure(
        val cause: Throwable? = null,
        val message: String? = cause?.message ?: DEFAULT_FAILURE_MESSAGE
    ) : FormState()


    val isFillable get() = this is Fillable
    val isValidating get() = this is Validating
    val isSubmitting get() = this is Submitting
    val isSubmitted get() = this is Submitted
    val isFailure get() = this is Failure

    val asFailure get() = this as Failure

    override fun toString(): String = "FormState"
}