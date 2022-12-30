@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.forms

import actions.Action1
import actions.constructors.action0I1R
import kase.Failure
import kase.FormState
import kase.Pending
import kase.Submitting
import kase.Success
import kase.Validating
import koncurrent.FailedLater
import koncurrent.Thenable
import presenters.collections.*
import presenters.fields.Invalid
import presenters.fields.Valid
import presenters.fields.ValidationResult
import presenters.fields.throwIfInvalid
import viewmodel.ViewModel
import kotlin.js.JsExport

open class Form<out F : Fields, P, R>(
    open val heading: String,
    open val details: String,
    open val fields: F,
    open val config: FormConfig<P>,
    initializer: FormActionsBuildingBlock<P, R>,
) : ViewModel<FormState<R>>(config.of(Pending)) {

    private val builtActions = FormActionsBuilder<P, R>().apply { initializer() }

    val cancelAction = action0I1R("Cancel") {
        val handler = builtActions.actions.firstOrNull {
            it.name.contentEquals("Cancel", ignoreCase = true)
        }?.handler ?: { logger.warn("Cancel action of ${this::class.simpleName} was never setup") }
        handler()
    }

    private val submitAction: Action1<P, R> = builtActions.submitAction

    private val codec get() = config.codec

    fun cancel() = try {
        cancelAction.invoke()
    } catch (err: Throwable) {
        ui.value = Failure(err)
    }

    fun exit() = cancel()

    open fun validate(): ValidationResult {
        fields.validate()
        val invalids = fields.allInvalid
        if (invalids.isNotEmpty()) {
            val message = simpleTableOf(invalids) {
                column("Field") { it.item.label.capitalizedWithoutAstrix() }
                column("Value") { it.item.output.value.toString() }
                column("Reason") { it.item.feedback.value.asError?.message ?: "Unknown" }
            }.renderToString()
            logger.error(message)
            val invalidFields = IllegalArgumentException(message)
            val size = invalids.size
            val terminator = "input" + if (size > 1) "s" else ""
            return Invalid(IllegalArgumentException("You have $size invalid $terminator", invalidFields))
        }
        return Valid
    }

    fun clear() {
        fields.clearAll()
        ui.value = Pending
    }

    fun submit(): Thenable<R> = try {
        ui.value = Validating
        validate().throwIfInvalid()
        val values = fields.encodedValuesToJson(codec)
        ui.value = Submitting(values)
        submitAction.invoke(codec.decodeFromString(config.serializer, values)).then {
            ui.value = Success(it)
            if (config.exitOnSubmitted) cancel()
            it
        }.catch {
            ui.value = Failure(it) { onRetry { submit() } }
            throw it
        }
    } catch (err: Throwable) {
        ui.value = Failure(err) { onRetry { submit() } }
        FailedLater(err, config.executor)
    }
}