@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.forms

import presenters.cases.Failure as FailureCase
import kotlin.js.JsExport

sealed class BaseFormState<out F : BaseForm<*, *>> {
    abstract val form: F

    data class Fillable<out F : BaseForm<*, *>>(override val form: F) : BaseFormState<F>()
    data class Submitting<out F : BaseForm<*, *>>(override val form: F) : BaseFormState<F>()
    data class Submitted<out F : BaseForm<*, *>>(override val form: F) : BaseFormState<F>()
    data class Failure<out F : BaseForm<*, *>>(
        override val form: F,
        val cause: Throwable? = null,
        val message: String? = cause?.message ?: FailureCase.DEFAULT_MESSAGE
    ) : BaseFormState<F>()


    val isFillable get() = this is Fillable
    val isSubmitting get() = this is Submitting
    val isSubmitted get() = this is Submitted
    val isFailure get() = this is Failure

    val asFillable get() = this as Fillable
    val asSubmitting get() = this as Submitting
    val asSubmitted get() = this as Submitted
    val asFailure get() = this as Failure
}