@file:JsExport

package presenters

import presenters.properties.Settable
import presenters.validation.Validateable
import kotlin.js.JsExport

interface BooleanInputField : InputField, CommonInputProperties, Settable<Boolean>, SerializableLiveData<Boolean>, Validateable<Boolean> {
    fun toggle()
}