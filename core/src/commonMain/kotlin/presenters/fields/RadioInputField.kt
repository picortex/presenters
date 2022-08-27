@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import presenters.fields.internal.BooleanBasedInputField
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.reflect.KProperty

class RadioInputField(
    override val name: String,
    override val label: String = name,
    override val defaultValue: Boolean? = ValuedField.DEFAULT_VALUE,
    override val isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    override val isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    override val validator: ((Boolean?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) : BooleanBasedInputField(name, label, defaultValue, isReadonly, isRequired, validator) {
    @JsName("_ignore_fromPropery")
    constructor(
        name: KProperty<*>,
        label: String = name.name,
        defaultValue: Boolean? = ValuedField.DEFAULT_VALUE,
        isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
        isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
        validator: ((Boolean?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
    ) : this(name.name, label, defaultValue, isReadonly, isRequired, validator)
}