@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import presenters.fields.internal.BooleanBasedInputField
import kotlin.js.JsExport

class CheckBoxInputField(
    override val name: String,
    override val isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    override val defaultValue: Boolean? = SingleValuedField.DEFAULT_VALUE,
    override val isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    validator: ((Boolean?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) : BooleanBasedInputField(name, isRequired, label, defaultValue, isReadonly, validator)