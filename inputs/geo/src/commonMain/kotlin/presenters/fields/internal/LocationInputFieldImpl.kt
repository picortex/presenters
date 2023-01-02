package presenters.fields.internal

import geo.GeoLocation
import kotlinx.serialization.KSerializer
import live.mutableLiveOf
import presenters.fields.InputLabel
import presenters.fields.LocationInputField
import presenters.fields.RawData
import presenters.fields.SingleValuedField
import presenters.internal.GooglePlacesApiParser
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

@PublishedApi
internal class LocationInputFieldImpl(
    name: String,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    label: InputLabel = InputLabel(name, isRequired),
    override val hint: String = label.text,
    defaultValue: String? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    private val googleParser: GooglePlacesApiParser = GooglePlacesApiParser(),
    validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) : TransformedInputValuedField<String, GeoLocation>(name, isRequired, label, defaultValue, null, { googleParser.parseOrNull(it) }, isReadonly, validator), LocationInputField {
    override val serializer: KSerializer<GeoLocation> by lazy { GeoLocation.serializer() }

    override fun validate(value: String?): ValidationResult {
        val tag = label.capitalizedWithoutAstrix()
        if (isRequired && value == null) {
            return Invalid(IllegalArgumentException("$tag is required"))
        }

        return try {
            validator?.invoke(value)
            Valid
        } catch (err: Throwable) {
            Invalid(err)
        }
    }
}