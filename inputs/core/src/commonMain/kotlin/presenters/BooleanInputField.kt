@file:JsExport

package presenters

import presenters.properties.Clearable
import presenters.properties.Hintable
import presenters.properties.Labeled
import presenters.properties.Mutability
import presenters.properties.Requireble
import presenters.properties.Settable
import presenters.validation.Validateable
import kotlin.js.JsExport

interface BooleanInputField : InputField, Labeled, Hintable, Mutability, Requireble, Settable<Boolean>, LiveOutputData<Boolean>, Validateable<Boolean>, Clearable {
    fun toggle()
}