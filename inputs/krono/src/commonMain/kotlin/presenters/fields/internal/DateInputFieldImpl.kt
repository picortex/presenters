package presenters.fields.internal

import kotlinx.serialization.KSerializer
import krono.LocalDate
import krono.LocalDateOrNull
import krono.serializers.LocalDateIsoSerializer
import live.MutableLive
import live.mutableLiveOf
import presenters.DateInputField
import presenters.fields.InputFieldState
import presenters.Label
import presenters.fields.Formatter
import presenters.internal.utils.Clearer
import presenters.internal.utils.DataTransformer
import presenters.internal.utils.FormattedOutputSetter
import presenters.internal.validators.CompoundValidator
import presenters.internal.validators.LambdaValidator
import presenters.internal.validators.ClippingValidator
import presenters.internal.validators.RequirementValidator

@PublishedApi
internal class DateInputFieldImpl(
    override val name: String,
    override val isRequired: Boolean = false,
    override val label: Label = Label(name, isRequired),
    override val hint: String = label.text,
    private val value: LocalDate? = null,
    override val isReadonly: Boolean = false,
    override val pattern: String = DEFAULT_PATTERN,
    override val max: LocalDate? = DEFAULT_MAX_DATE,
    override val min: LocalDate? = DEFAULT_MIN_DATE,
    private val validator: ((LocalDate?) -> Unit)? = null
) : DateInputField {
    private val default = FormattedData<String, LocalDate>(null, "", value)
    override val data = mutableLiveOf(default)
    override val serializer: KSerializer<LocalDate> = LocalDateIsoSerializer
    override val feedback: MutableLive<InputFieldState> = mutableLiveOf(InputFieldState.Empty)
    override val formatter: Formatter<LocalDate> = DEFAULT_FORMATTER
    override val transformer: (String?) -> LocalDate? = DEFAULT_DATE_TRANSFORMER

    private val dv1 = CompoundValidator(
        feedback,
        RequirementValidator(feedback, label.capitalizedWithoutAstrix(), isRequired),
        ClippingValidator(feedback, label.capitalizedWithoutAstrix(), max, min),
        LambdaValidator(feedback, validator)
    )

    private val trnsfrm = DataTransformer(formatter, transformer)
    private val setter = FormattedOutputSetter(data, feedback, trnsfrm, dv1)

    override fun set(value: String) = setter.set(value)

    private val clearer = Clearer(default, data, feedback)
    override fun clear() = clearer.clear()

    override fun validate(value: LocalDate?) = dv1.validate(value)
    override fun validate() = dv1.validate(data.value.output)
    override fun validateSettingInvalidsAsErrors(value: LocalDate?) = dv1.validateSettingInvalidsAsErrors(value)
    override fun validateSettingInvalidsAsErrors() = dv1.validateSettingInvalidsAsErrors(data.value.output)
    override fun validateSettingInvalidsAsWarnings(value: LocalDate?) = dv1.validateSettingInvalidsAsWarnings(value)
    override fun validateSettingInvalidsAsWarnings() = dv1.validateSettingInvalidsAsWarnings(data.value.output)

    companion object {
        val DEFAULT_MAX_DATE: Nothing? = null
        val DEFAULT_MIN_DATE: Nothing? = null
        val DEFAULT_PATTERN = "{MMM} {D}, {YYYY}"
        val DEFAULT_FORMATTER = Formatter<LocalDate> { it?.toIsoString() }
        val DEFAULT_DATE_TRANSFORMER = { iso: String? -> LocalDateOrNull(iso) }
    }
}