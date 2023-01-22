@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package presenters

import presenters.properties.Settable
import presenters.validation.Validateable
import kotlin.js.JsExport

interface TransformingInputField<I, O> : InputField, CommonInputProperties, Settable<I>, SerializableLiveFormattedData<I, O>, Validateable<O>