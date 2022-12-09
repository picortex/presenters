@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import presenters.fields.internal.BooleanBasedInputField
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.reflect.KProperty

class CheckBoxInputField(
    override val name: String,
    override val isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    override val defaultValue: Boolean? = ValuedField.DEFAULT_VALUE,
    override val isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    validator: ((Boolean?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) : BooleanBasedInputField(name, isRequired, label, defaultValue, isReadonly, validator)