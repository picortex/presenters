package presenters.internal

import geo.GeoLocation
import kotlinx.serialization.KSerializer
import live.MutableLive
import live.mutableLiveOf
import presenters.InputFieldState
import presenters.Label
import presenters.LocationInputField
import presenters.internal.utils.Clearer
import presenters.internal.utils.DataTransformer
import presenters.internal.utils.FormattedOutputSetter
import presenters.internal.utils.Typer
import presenters.internal.validators.CompoundValidator
import presenters.internal.validators.LambdaValidator
import presenters.internal.validators.RequirementValidator

@PublishedApi
internal class LocationInputFieldImpl(
    override val name: String,
    override val isRequired: Boolean,
    override val label: Label,
    override val hint: String,
    private val value: GeoLocation?,
    override val isReadonly: Boolean,
    private val validator: ((GeoLocation?) -> Unit)?
) : TransformedDataField<String, GeoLocation>(value), LocationInputField {
    override val serializer = GeoLocation.serializer()
    override val transformer = DataTransformer<String, GeoLocation>({ it.toString() }, { googleParser.parseOrNull(it) })

    override val cv = CompoundValidator(
        data, feedback,
        RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        LambdaValidator(data, feedback, validator)
    )

    override fun type(text: String) = Typer(data.value.input, setter).type(text)

    private companion object {
        val googleParser: GooglePlacesApiParser = GooglePlacesApiParser()
    }
}