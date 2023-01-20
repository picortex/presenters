@file:JsExport

package presenters

import presenters.properties.Clearable
import presenters.properties.Hintable
import presenters.properties.Labeled
import presenters.properties.Mutability
import presenters.properties.Requireble
import presenters.properties.Settable
import presenters.validation.Validateable0
import presenters.validation.Validateable1
import kotlin.js.JsExport

interface BooleanInputField : InputField, Labeled, Hintable, Mutability, Requireble, Settable<Boolean>, LiveOutputData<Boolean>, Validateable1<Boolean>, Validateable0, Clearable {
    fun toggle()
}