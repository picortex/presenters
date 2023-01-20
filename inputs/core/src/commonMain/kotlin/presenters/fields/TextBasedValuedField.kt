@file:JsExport

package presenters.fields

import presenters.fields.properties.Hintable
import presenters.fields.properties.Requireble
import presenters.fields.properties.Typeable
import kotlin.js.JsExport

interface TextBasedValuedField<O> : ValuedField<O>, Typeable, Hintable, Requireble