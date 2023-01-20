package presenters.fields.internal

import geo.GeoLocation
import presenters.Label
import presenters.fields.LocationInputField
import presenters.fields.SingleValuedField
import presenters.internal.GooglePlacesApiParser
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

@PublishedApi
internal class LocationInputFieldImpl(
    name: String,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    label: Label = Label(name, isRequired),
    hint: String = label.text,
    defaultValue: String? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) : TextBasedValuedFieldImpl<GeoLocation>(
    name = name, isRequired = isRequired, label = label, hint = hint,
    formatter = null, transformer = { googleParser.parseOrNull(it) },
    defaultValue = defaultValue, isReadonly = isReadonly, validator = validator,
    serializer = GeoLocation.serializer()
), LocationInputField {
    override fun validate(value: String?): ValidationResult {
//        super.validate(value)
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

    private companion object {
        val googleParser: GooglePlacesApiParser = GooglePlacesApiParser()
    }
}