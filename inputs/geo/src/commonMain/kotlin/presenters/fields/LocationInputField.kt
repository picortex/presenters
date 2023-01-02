@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import geo.GeoLocation
import kotlinx.serialization.KSerializer
import presenters.fields.internal.AbstractValuedField
import presenters.internal.GooglePlacesApiParser
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult
import kotlin.js.JsExport

typealias LocationInputField = TextBasedValuedField<GeoLocation>