package presenters.internal

import kotlinx.serialization.KSerializer
import krono.LocalDate
import krono.serializers.LocalDateIsoSerializer
import live.MutableLive
import live.mutableLiveOf
import presenters.DateInputField
import presenters.DateRangeInputField
import presenters.Label
import presenters.Range
import presenters.InputFieldState
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
    private val defaultStart: LocalDate?,
    private val defaultEnd: LocalDate?,
    override val isReadonly: Boolean,
    override val pattern: String,
    override val max: LocalDate?,
    override val min: LocalDate?,
    validator: ((Range<LocalDate>?) -> Unit)?
) : DateRangeInputField {
    private val default = FormattedData<String, Range<LocalDate>>(null, "", Range.of(defaultStart, defaultEnd))
    override val data = mutableLiveOf(default)
    override val feedback: MutableLive<InputFieldState> = mutableLiveOf(InputFieldState.Empty)
    override val transformer: (String?) -> LocalDate? = DateInputFieldImpl.DEFAULT_DATE_TRANSFORMER

    override val start = DateInputField(
        name = "$name-start", isRequired,
        label = "$name start",
        hint = "Start Date",
        value = defaultStart,
        isReadonly = isReadonly,
        pattern = pattern,
        maxDate = max,
        minDate = min
    )

    override val end = DateInputField(
        name = "$name-end", isRequired,
        label = "$name end",
        hint = "End Date",
        value = defaultEnd,
        isReadonly = isReadonly,
        pattern = pattern,
        maxDate = max,
        minDate = min
    )

    private val drv = CompoundValidator(
        data, feedback,
        RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        RangeValidator(data, feedback, isRequired, label.capitalizedWithoutAstrix(), max, min),
        LambdaValidator(data, feedback, validator)
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
    override fun validateSettingInvalidsAsErrors(value: Range<LocalDate>?) = drv.validateSettingInvalidsAsErrors(value)
    override fun validateSettingInvalidsAsWarnings(value: Range<LocalDate>?) = drv.validateSettingInvalidsAsWarnings(value)

    override val serializer: KSerializer<Range<LocalDate>> = inputSerializer

    private companion object {
        val inputSerializer = Range.serializer(LocalDateIsoSerializer)
    }
}