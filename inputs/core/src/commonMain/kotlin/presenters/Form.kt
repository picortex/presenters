@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters

import actions.Action1
import actions.action0I1R
import kase.Failure
import kase.FormState
import kase.Pending
import kase.Submitting
import kase.Validating
import kase.toFormState
import kollections.toIList
import koncurrent.FailedLater
import koncurrent.Later
import koncurrent.later.finally
import presenters.collections.renderToString
import presenters.collections.simpleTableOf
import presenters.exceptions.FormValidationException
import presenters.properties.Labeled
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.Validateable
import presenters.validation.ValidationResult
import presenters.validation.throwIfInvalid
import viewmodel.ViewModel
import kotlin.js.JsExport

open class Form<out F : Fields, out P, out R>(
    open val heading: String,
    open val details: String,
    open val fields: F,
    open val config: FormConfig<@UnsafeVariance P>,
    initializer: FormActionsBuildingBlock<P, R>,
) : ViewModel<FormState<R>>(config.of(Pending)) {

    private val builtActions = FormActionsBuilder<P, R>().apply { initializer() }

    val cancelAction = action0I1R("Cancel") {
        val handler = builtActions.actions.firstOrNull {
            it.name.contentEquals("Cancel", ignoreCase = true)
        }?.handler ?: { logger.warn("Cancel action of ${this::class.simpleName} was never setup") }
        handler()
    }

    private val submitAction: Action1<P, Later<R>> = builtActions.submitAction

    private val codec get() = config.codec

    val exitOnSubmitted get() = config.exitOnSubmitted

    fun cancel() = try {
        cancelAction.invoke()
    } catch (err: Throwable) {
        ui.value = Failure(err)
    }

    fun exit() {
        ui.value = Pending
        cancel()
    }

    private fun Collection<SerializableLiveData<out Any?>>.errorTable() = simpleTableOf(this) {
        column("Field") {
            (it.item as? Labeled)?.label?.capitalizedWithAstrix() ?: it.item.name
        }
        column("Value") {
            it.item.data.value.output.toString()
        }
        column("Reason") {
            (it.item as? Validateable<Any?>)?.feedback?.value?.asError?.message ?: "Unknown"
        }
    }.renderToString()

    fun validate(): ValidationResult {
        val invalids = fields.validate()

        if (invalids.isEmpty()) return Valid

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

    fun clear() {
        fields.clearAll()
        ui.value = Pending
        ui.history.clear()
    }

    fun submit(): Later<R> = try {
        ui.value = Validating
        validate().throwIfInvalid()
        val values = fields.encodedValuesToJson(codec)
        ui.value = Submitting(values)
        submitAction.invoke(codec.decodeFromString(config.serializer, values)).finally {
            ui.value = it.toFormState { onRetry { submit() } }
            if (exitOnSubmitted) exit()
        }
    } catch (err: Throwable) {
        ui.value = Failure(err) { onRetry { submit() } }
        FailedLater(err, config.executor)
    }
}