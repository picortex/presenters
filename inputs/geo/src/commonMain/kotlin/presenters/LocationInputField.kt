@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters

import geo.GeoLocation
import presenters.properties.Typeable
import kotlin.js.JsExport

interface LocationInputField : TransformingInputField<String, GeoLocation>, Typeable