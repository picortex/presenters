@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters

import presenters.properties.Clearable
import presenters.properties.Hintable
import presenters.properties.Labeled
import presenters.properties.Mutability
import presenters.properties.Requireble
import presenters.properties.Settable
import presenters.validation.Validateable
import kotlin.js.JsExport

interface TransformingInputField<I, O> : InputField, Labeled, Hintable, Mutability, Requireble, Settable<I>, LiveFormattedData<I, O>, Validateable<O>, Clearable