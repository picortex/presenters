@file:JsExport

package presenters

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

interface TextInputField : InputField, Labeled, Hintable, Mutability, Requireble, Settable<String>, LiveOutputData<String>, Validateable1<String>, Validateable0, Typeable, Clearable {
    val maxLength: Int?
    val minLength: Int?
}