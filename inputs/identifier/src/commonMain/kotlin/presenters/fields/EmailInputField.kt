@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import identifier.Email
import presenters.fields.internal.TextBasedValueField
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.reflect.KProperty

@JsExport
class EmailInputField(
    override val name: String,
    override val label: String = name,
    override val hint: String = label,
    override val defaultValue: String? = ValuedField.DEFAULT_VALUE,
    override val isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    override val isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    override val maxLength: Int? = DEFAULT_MAX_LENGTH,
    override val minLength: Int? = DEFAULT_MIN_LENGTH,
    validator: ((String?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) : TextBasedValueField(name, label, hint, defaultValue, isReadonly, isRequired, maxLength, minLength, validator) {

    @JsName("_ignore_fromPropery")
    constructor(
        name: KProperty<*>,
        label: String = name.name,
        hint: String = label,
        defaultValue: String? = ValuedField.DEFAULT_VALUE,
        isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
        isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
        maxLength: Int? = DEFAULT_MAX_LENGTH,
        minLength: Int? = DEFAULT_MIN_LENGTH,
        validator: ((String?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
    ) : this(name.name, label, hint, defaultValue, isReadonly, isRequired, maxLength, minLength, validator)

    override fun validate(value: String?) {
        super.validate(value)
        if (value != null) Email(value)
    }
}