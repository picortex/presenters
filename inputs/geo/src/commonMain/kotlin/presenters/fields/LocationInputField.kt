@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import geo.GeoLocation
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.nullable
import presenters.fields.internal.AbstractValuedField
import presenters.internal.GooglePlacesApiParser
import kotlin.js.JsExport

@JsExport
class LocationInputField(
    override val name: String,
    override val isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    val hint: String = label.text,
    override val defaultValue: String? = SingleValuedField.DEFAULT_VALUE,
    override val isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    private val googleParser: GooglePlacesApiParser = GooglePlacesApiParser(),
    validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) : AbstractValuedField<String?, GeoLocation>(name, isRequired, label, defaultValue, { googleParser.parseOrNull(it) }, isReadonly, validator) {
    override val serializer: KSerializer<GeoLocation?> by lazy { GeoLocation.serializer().nullable }

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