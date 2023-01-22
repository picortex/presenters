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
) : LocationInputField {
    private val default = FormattedData<String, GeoLocation>(null, "", value)
    override val data = mutableLiveOf(default)
    override val serializer: KSerializer<GeoLocation> = GeoLocation.serializer()
    override val feedback: MutableLive<InputFieldState> = mutableLiveOf(InputFieldState.Empty)
    private val transformer = DataTransformer<String, GeoLocation>({ it.toString() }, { googleParser.parseOrNull(it) })

    private val lv = CompoundValidator(
        data, feedback,
        RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        LambdaValidator(data, feedback, validator)
    )

    private val setter = FormattedOutputSetter(data, feedback, transformer, lv)
    override fun set(value: String) = setter.set(value)

    override fun type(text: String) = Typer(data.value.input, setter).type(text)

    private val clearer = Clearer(default, data, feedback)
    override fun clear() = clearer.clear()

    override fun validate(value: GeoLocation?) = lv.validate(value)
    override fun validateSettingInvalidsAsErrors(value: GeoLocation?) = lv.validateSettingInvalidsAsErrors(value)
    override fun validateSettingInvalidsAsWarnings(value: GeoLocation?) = lv.validateSettingInvalidsAsWarnings(value)

    private companion object {
        val googleParser: GooglePlacesApiParser = GooglePlacesApiParser()
    }
}