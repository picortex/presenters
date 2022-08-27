@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import presenters.fields.internal.AbstractTextInputFieldRaw
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.reflect.KProperty

@Deprecated("In favour of [Integer,Long,Double]InputField")
class NumberInputField(
    override val name: String,
    override val label: String = name,
    override val hint: String = label,
    value: String? = null,
    override val isReadonly: Boolean = InputFieldWithValue.DEFAULT_IS_READONLY,
    override val isRequired: Boolean = InputFieldWithValue.DEFAULT_IS_REQUIRED,
    override val validator: (String?) -> String? = { it }
) : AbstractTextInputFieldRaw(name, label, hint, value, isReadonly, isRequired, validator) {
    @JsName("_ignore_fromPropery")
    constructor(
        name: KProperty<*>,
        label: String = name.name,
        hint: String = label,
        value: String? = null,
        isReadonly: Boolean = InputFieldWithValue.DEFAULT_IS_READONLY,
        isRequired: Boolean = InputFieldWithValue.DEFAULT_IS_REQUIRED,
        validator: (String?) -> String? = { it }
    ) : this(name.name, label, hint, value, isReadonly, isRequired, validator)
}