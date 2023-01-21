package presenters.internal

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
import presenters.fields.internal.FormattedData
import presenters.internal.utils.Clearer
import presenters.internal.utils.DataTransformer
import presenters.internal.utils.FormattedOutputSetter
import presenters.internal.utils.Typer
import presenters.internal.validators.CompoundValidator
import presenters.internal.validators.LambdaValidator
import presenters.internal.validators.ClippingValidator
import presenters.internal.validators.RequirementValidator

@PublishedApi
internal class DateInputFieldImpl(
    override val name: String,
    override val isRequired: Boolean,
    override val label: Label,
    override val hint: String,
    private val value: LocalDate?,
    override val isReadonly: Boolean,
    override val pattern: String,
    override val max: LocalDate?,
    override val min: LocalDate?,
    private val validator: ((LocalDate?) -> Unit)?
) : DateInputField {
    private val default = FormattedData<String, LocalDate>(null, "", value)
    override val data = mutableLiveOf(default)
    override val serializer: KSerializer<LocalDate> = LocalDateIsoSerializer
    override val feedback: MutableLive<InputFieldState> = mutableLiveOf(InputFieldState.Empty)
    override val formatter: Formatter<LocalDate> = DEFAULT_FORMATTER
    override val transformer: (String?) -> LocalDate? = DEFAULT_DATE_TRANSFORMER

    private val dv1 = CompoundValidator(
        data, feedback,
        RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        ClippingValidator(data, feedback, label.capitalizedWithoutAstrix(), max, min),
        LambdaValidator(data, feedback, validator)
    )

    private val trnsfrm = DataTransformer(formatter, transformer)
    private val setter = FormattedOutputSetter(data, feedback, trnsfrm, dv1)

    override fun set(value: String) = setter.set(value)

    override fun type(text: String) = Typer(data.value.input, setter).type(text)

    private val clearer = Clearer(default, data, feedback)
    override fun clear() = clearer.clear()

    override fun validate(value: LocalDate?) = dv1.validate(value)
    override fun validateSettingInvalidsAsErrors(value: LocalDate?) = dv1.validateSettingInvalidsAsErrors(value)
    override fun validateSettingInvalidsAsWarnings(value: LocalDate?) = dv1.validateSettingInvalidsAsWarnings(value)

    companion object {
        val DEFAULT_PATTERN = "{MMM} {D}, {YYYY}"
        val DEFAULT_FORMATTER = Formatter<LocalDate> { it?.toIsoString() }
        val DEFAULT_DATE_TRANSFORMER = { iso: String? -> if (iso != null) LocalDate(iso) else null }
    }
}