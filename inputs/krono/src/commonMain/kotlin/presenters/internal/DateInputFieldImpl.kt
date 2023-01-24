package presenters.internal

import kotlinx.serialization.KSerializer
import krono.LocalDate
import krono.serializers.LocalDateIsoSerializer
import live.MutableLive
import live.mutableLiveOf
import presenters.DateInputField
import presenters.InputFieldState
import presenters.Label
import presenters.Formatter
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
    validator: ((LocalDate?) -> Unit)?
) : TransformedDataField<String, LocalDate>(value), DateInputField {

    override val serializer: KSerializer<LocalDate> = LocalDateIsoSerializer

    override val cv = CompoundValidator(
        data, feedback,
        RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        ClippingValidator(data, feedback, label.capitalizedWithoutAstrix(), max, min),
        LambdaValidator(data, feedback, validator)
    )

    override val transformer = DATE_DATE_TRANSFORMER

    override fun type(text: String) = Typer(data.value.input, setter).type(text)

    companion object {
        val DEFAULT_PATTERN = "{MMM} {D}, {YYYY}"
        val DEFAULT_FORMATTER = Formatter<LocalDate> { it?.toIsoString() }
        val DEFAULT_DATE_TRANSFORMER = { iso: String? -> if (iso != null) LocalDate(iso) else null }
        val DATE_DATE_TRANSFORMER = DataTransformer(DEFAULT_FORMATTER, DEFAULT_DATE_TRANSFORMER)
    }
}