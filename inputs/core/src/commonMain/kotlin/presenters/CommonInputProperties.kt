@file:JsExport

package presenters

import presenters.properties.Clearable
import presenters.properties.Hintable
import presenters.properties.Labeled
import presenters.properties.Mutability
import presenters.properties.Requireble
import kotlin.js.JsExport

interface CommonInputProperties : Labeled, Hintable, Mutability, Requireble, Clearable