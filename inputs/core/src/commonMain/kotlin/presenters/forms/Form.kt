@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.forms

import actions.Action1
import actions.action0I1R
import kase.Failure
import kase.FormState
import kase.Pending
import kase.Submitting
import kase.Success
import kase.Validating
import kollections.toIList
import koncurrent.FailedLater
import koncurrent.Thenable
import presenters.LiveOutputData
import presenters.collections.*
import presenters.exceptions.FormValidationException
import presenters.fields.properties.Labeled
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.Validateable0
import presenters.validation.ValidationResult
import presenters.validation.throwIfInvalid
import viewmodel.ViewModel
import kotlin.js.JsExport

open class Form<out F : Fields, P, out R>(
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

    private val codec = config.codec

    fun cancel() = try {
        cancelAction.invoke()
    } catch (err: Throwable) {
        ui.value = Failure(err)
    }

    fun exit() = cancel()

    private fun Collection<LiveOutputData<*>>.errorTable() = simpleTableOf(this) {
        column("Field") {
            (it.item as? Labeled)?.label?.capitalizedWithAstrix() ?: it.item.name
        }
        column("Value") {
            it.item.data.value.output.toString()
        }
        column("Reason") {
            (it.item as? Validateable0)?.feedback?.value?.asError?.message ?: "Unknown"
        }
    }.renderToString()

    fun validate(): ValidationResult {
        fields.validate()
        val invalids = fields.allInvalid
        if (invalids.isNotEmpty()) {
            val size = invalids.size
            val terminator = "input" + if (size > 1) "s" else ""
            val exception = FormValidationException(
                message = "You have $size invalid $terminator",
                errors = invalids.errorTable(),
                fields = invalids.toIList()
            )
            logger.error(exception.message)
            logger.error(exception.errors)
            return Invalid(exception)
        }
        return Valid
    }

    fun clear() {
        fields.clearAll()
        ui.value = Pending
        ui.history.clear()
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