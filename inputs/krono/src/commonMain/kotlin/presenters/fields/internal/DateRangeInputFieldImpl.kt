package presenters.fields.internal

import kotlinx.serialization.KSerializer
import krono.LocalDate
import krono.serializers.LocalDateIsoSerializer
import live.MutableLive
import live.mutableLiveOf
import presenters.DateRangeInputField
import presenters.Label
import presenters.Range
import presenters.fields.InputFieldState
import presenters.internal.utils.Clearer
import presenters.internal.validators.CompoundValidator
import presenters.internal.validators.LambdaValidator
import presenters.internal.validators.RangeValidator
import presenters.internal.validators.RequirementValidator

@PublishedApi
internal class DateRangeInputFieldImpl(
    override val name: String,
    override val isRequired: Boolean,
    override val label: Label,
    override val hint: String,
    override val limit: Range<LocalDate>?,
    private val defaultStart: LocalDate?,
    private val defaultEnd: LocalDate?,
    override val isReadonly: Boolean,
    validator: ((Range<LocalDate>?) -> Unit)?
) : DateRangeInputField {
    private val default = FormattedData<String, Range<LocalDate>>(null, "", Range.of(defaultStart, defaultEnd))
    override val data = mutableLiveOf(default)
    override val feedback: MutableLive<InputFieldState> = mutableLiveOf(InputFieldState.Empty)
    override val transformer: (String?) -> LocalDate? = DateInputFieldImpl.DEFAULT_DATE_TRANSFORMER

    override val start = DateInputFieldImpl(
        name = "$name-start", isRequired,
        label = Label("$name start", isRequired),
        hint = "Start Date",
        value = defaultStart,
        isReadonly = isReadonly,
        max = limit?.end,
        min = limit?.start
    )

    override val end = DateInputFieldImpl(
        name = "$name-end", isRequired,
        label = Label("$name start", isRequired),
        hint = "Start Date",
        value = defaultEnd,
        isReadonly = isReadonly,
        max = limit?.end,
        min = limit?.start
    )

    private val drv = CompoundValidator(
        feedback,
        RequirementValidator(feedback, label.capitalizedWithoutAstrix(), isRequired),
        RangeValidator(feedback, isRequired, label.capitalizedWithoutAstrix(), limit),
        LambdaValidator(feedback, validator)
    )

    private fun update(s: LocalDate?, e: LocalDate?) {
        if (s != null && e != null) {
            if (s <= e) {
                data.value = FormattedData("", "", Range(s, e))
                feedback.value = InputFieldState.Empty
            } else {
                data.value = FormattedData("", "", null)
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

    private val clearer = Clearer(default, data, feedback)
    override fun clear() {
        start.clear()
        end.clear()
        clearer.clear()
    }

    override fun setStart(value: String) {
        start.set(value)
        val s = start.data.value.output
        val e = end.data.value.output
        update(s, e)
    }

    override fun setEnd(value: String) {
        end.set(value)
        val s = start.data.value.output
        val e = end.data.value.output
        update(s, e)
    }

    override fun validate(value: Range<LocalDate>?) = drv.validate(value)
    override fun validate() = drv.validate(data.value.output)
    override fun validateSettingInvalidsAsErrors(value: Range<LocalDate>?) = drv.validateSettingInvalidsAsErrors(value)
    override fun validateSettingInvalidsAsErrors() = drv.validateSettingInvalidsAsErrors(data.value.output)
    override fun validateSettingInvalidsAsWarnings(value: Range<LocalDate>?) = drv.validateSettingInvalidsAsWarnings(value)
    override fun validateSettingInvalidsAsWarnings() = drv.validateSettingInvalidsAsWarnings(data.value.output)

    override val serializer: KSerializer<Range<LocalDate>> = inputSerializer

    private companion object {
        val inputSerializer = Range.serializer(LocalDateIsoSerializer)
    }
}