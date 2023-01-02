@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import kotlinx.serialization.KSerializer
import krono.LocalDate
import krono.serializers.LocalDateIsoSerializer
import live.Live
import live.MutableLive
import live.mutableLiveOf
import presenters.fields.internal.AbstractRangeField
import presenters.fields.internal.DateInputFieldImpl
import presenters.fields.internal.OutputData
import kotlin.js.JsExport

class DateRangeInputField(
    override val name: String,
    override val isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    override val limit: Range<LocalDate>? = null,
    defaultStart: LocalDate? = null,
    defaultEnd: LocalDate? = null,
    override val isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    validator: ((LocalDate?, LocalDate?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) : AbstractRangeField<String, LocalDate>(name, isRequired, label, DateInputFieldImpl.DEFAULT_DATE_TRANSFORMER, limit, isReadonly, validator) {

    override val data = mutableLiveOf(OutputData(Range.of(defaultStart, defaultEnd)))

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
                data.value = OutputData(Range(s, e))
                feedback.value = InputFieldState.Empty
            } else {
                data.value = OutputData(null)
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
//        start.data.value = value
        start.set(value)
        val s = start.data.value.output
        val e = end.data.value.output
        update(s, e)
    }

    override fun setEnd(value: String?) {
//        end.input.value = value
        end.set(value)
        val s = start.data.value.output
        val e = end.data.value.output
        update(s, e)
    }

    override val serializer: KSerializer<Range<LocalDate>> = inputSerializer

    private companion object {
        val inputSerializer = Range.serializer(LocalDateIsoSerializer)
    }
}