@file:JsExport

package presenters

import presenters.properties.Clearable
import presenters.properties.Hintable
import presenters.properties.Labeled
import presenters.properties.Mutability
import presenters.properties.Requireble
import presenters.properties.Settable
import presenters.properties.Typeable
import presenters.validation.Validateable0
import presenters.validation.Validateable1
import kotlin.js.JsExport

interface TextInputField : InputField, Labeled, Hintable, Mutability, Requireble, Settable<String>, LiveOutputData<String>, Validateable1<String>, Validateable0, Typeable, Clearable {
    val maxLength: Int?
    val minLength: Int?
}