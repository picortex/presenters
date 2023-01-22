@file:JsExport

package presenters

import presenters.properties.Clearable
import presenters.properties.Hintable
import presenters.properties.Labeled
import presenters.properties.Mutability
import presenters.properties.Requireble
import presenters.properties.Settable
import presenters.properties.Typeable
import presenters.validation.Validateable
import kotlin.js.JsExport

interface BasicTextInputField : InputField, Labeled, Hintable, Mutability, Requireble, Settable<String>, LiveOutputData<String>, Validateable<String>, Typeable, Clearable