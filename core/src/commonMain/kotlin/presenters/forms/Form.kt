@file:JsExport @file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.forms

import koncurrent.Later
import koncurrent.later.catch
import kotlinx.serialization.json.JsonObject
import presenters.actions.GenericAction
import presenters.actions.SimpleAction
import presenters.collections.*
import viewmodel.ViewModel
import kotlin.js.JsExport
import kotlin.js.JsName

open class Form<out F : Fields, in P>(
    override val heading: String,
    override val details: String,
    override val fields: F,
    private val config: FormConfig<P>,
    initializer: FormActionsBuildingBlock<P>,
) : ViewModel<FormState>(config.of(FormState.Fillable)), BaseForm<F, P> {

    private val builtActions = FormActionsBuilder<P>().apply { initializer() }

    override val cancel = builtActions.actions.firstOrNull {
        it.name.contentEquals("Cancel", ignoreCase = true)
    } ?: SimpleAction("Cancel") {}

    override val submit: GenericAction<P> = builtActions.submitAction

    private val codec get() = config.codec

    @JsName("handleCancel")
    fun cancel() {
        try {
            cancel.invoke()
        } catch (err: Throwable) {
            ui.value = FormState.Failure(err)
        }
    }

    fun exit() = cancel()

    override fun validate() {
        fields.validate()
        val invalids = fields.allInvalid
        if (invalids.isNotEmpty()) {
            val message = simpleTableOf(invalids) {
                column("Field") { it.item.label.replaceFirstChar { c -> c.uppercase() } }
                column("Value") { it.item.value.toString() }
                column("Reason") { it.item.feedback.value.asError.message }
            }.tabulateToString()
            logger.error(message)
            val invalidFields = IllegalArgumentException(message)
            val size = invalids.size
            val terminator = "input" + if (size > 1) "s" else ""
            throw IllegalArgumentException("You have $size invalid $terminator", invalidFields)
        }
    }

    fun clear() {
        fields.clearAll()
        ui.value = FormState.Fillable
    }

    @JsName("send")
    fun submit() = try {
        ui.value = FormState.Validating
        validate()
        val values = fields.encodedValuesToJson(codec)
        ui.value = FormState.Submitting(values)
        submit.invoke(codec.decodeFromString(config.serializer, values)).then {
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