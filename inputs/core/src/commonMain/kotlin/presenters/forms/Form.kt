@file:JsExport @file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.forms

import koncurrent.Later
import koncurrent.later.catch
import presenters.actions.GenericAction
import presenters.actions.MutableSimpleAction
import presenters.collections.*
import presenters.fields.Invalid
import presenters.fields.Valid
import presenters.fields.ValidationResult
import presenters.fields.throwIfInvalid
import viewmodel.ViewModel
import kotlin.js.JsExport

open class Form<out F : Fields, P>(
    open val heading: String,
    open val details: String,
    open val fields: F,
    open val config: FormConfig<P>,
    initializer: FormActionsBuildingBlock<P>,
) : ViewModel<FormState>(config.of(FormState.Fillable)) {

    private val builtActions = FormActionsBuilder<P>().apply { initializer() }

    val cancelAction = MutableSimpleAction("Cancel") {
        val handler = builtActions.actions.firstOrNull {
            it.name.contentEquals("Cancel", ignoreCase = true)
        }?.handler ?: { logger.warn("Cancel action of ${this::class.simpleName} was never setup") }
        handler()
    }

    private val submitAction: GenericAction<P> = builtActions.submitAction

    private val codec get() = config.codec

    fun cancel() {
        try {
            cancelAction.invoke()
        } catch (err: Throwable) {
            ui.value = FormState.Failure(err)
        }
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
        ui.value = FormState.Fillable
    }

    fun submit() = try {
        ui.value = FormState.Validating
        validate().throwIfInvalid()
        val values = fields.encodedValuesToJson(codec)
        ui.value = FormState.Submitting(values)
        submitAction.invoke(codec.decodeFromString(config.serializer, values)).then {
            ui.value = FormState.Submitted
            if (config.exitOnSubmitted) cancel()
            it
        }.catch {
            ui.value = FormState.Failure(it)
            throw it
        }
    } catch (err: Throwable) {
        ui.value = FormState.Failure(err)
        Later.reject(err, config.executor)
    }
}