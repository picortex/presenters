@file:JsExport

package presenters.fields

import presenters.InputField
import presenters.LiveOutputData
import presenters.fields.properties.Clearable
import presenters.fields.properties.Hintable
import presenters.fields.properties.Labeled
import presenters.fields.properties.Mutability
import presenters.fields.properties.Requireble
import presenters.fields.properties.Settable
import presenters.fields.properties.Typeable
import presenters.validation.Validateable0
import presenters.validation.Validateable1
import kotlin.js.JsExport

interface BooleanInputField : InputField, Labeled, Hintable, Mutability, Requireble, Settable<Boolean>, LiveOutputData<Boolean>, Validateable1<Boolean>, Validateable0, Clearable {
    fun toggle()
}