@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.nullable
import krono.LocalDate
import krono.serializers.LocalDateIsoSerializer
import live.mutableLiveOf
import presenters.fields.internal.AbstractRangeField
import kotlin.js.JsExport

@JsExport
class DateRangeInputField(
    override val name: String,
    override val isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    override val limit: Range<LocalDate>? = null,
    val defaultStart: LocalDate? = null,
    val defaultEnd: LocalDate? = null,
    override val isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    validator: ((String?, String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) : AbstractRangeField<String, LocalDate>(name, isRequired, label, DateInputField.DEFAULT_DATE_TRANSFORMER, limit, isReadonly, validator) {

    override val start = DateInputField(
        name = "$name-start", isRequired,
        label = InputLabel("$name start", isRequired),
        hint = "Start Date",
        defaultValue = defaultStart?.toIsoString(),
        isReadonly = isReadonly,
        maxDate = null,
    )

    override val end = DateInputField(
        name = "$name-end", isRequired,
        label = InputLabel("$name start", isRequired),
        hint = "Start Date",
        defaultValue = defaultEnd?.toIsoString(),
        isReadonly = isReadonly,
        maxDate = null,
    )

    private fun update(s: LocalDate?, e: LocalDate?) {
        if (s != null && e != null) {
            if (s <= e) {
                output.value = Range(s, e)
                feedback.value = InputFieldState.Empty
            } else {
                output.value = null
                val message = "${label.capitalizedWithoutAstrix()} can't range from $s to $e"
                feedback.value = InputFieldState.Warning(message, IllegalArgumentException(message))
            }
        } else if (s == null) {
            val message = "${label.capitalizedWithoutAstrix()} start is required"
            feedback.value = InputFieldState.Warning(message, IllegalStateException(message))
        } else if (e == null) {
            val message = "${label.capitalizedWithoutAstrix()} end is required"
            feedback.value = InputFieldState.Warning(message, IllegalStateException(message))
        }
    }

    override fun setStart(value: String?) {
        start.input.value = value
        val s = start.output.value
        val e = end.output.value
        update(s, e)
    }

    override fun setEnd(value: String?) {
        end.input.value = value
        val s = start.output.value
        val e = end.output.value
        update(s, e)
    }

    override val serializer: KSerializer<Range<LocalDate>?> by lazy { Range.serializer(LocalDateIsoSerializer).nullable }

    override val output = mutableLiveOf<Range<LocalDate>?>(null)
}