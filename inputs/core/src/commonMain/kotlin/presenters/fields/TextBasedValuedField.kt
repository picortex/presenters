@file:JsExport

package presenters.fields

import presenters.properties.Hintable
import presenters.properties.Requireble
import presenters.properties.Typeable
import kotlin.js.JsExport

interface TextBasedValuedField<O> : ValuedField<O>, Typeable, Hintable, Requireble