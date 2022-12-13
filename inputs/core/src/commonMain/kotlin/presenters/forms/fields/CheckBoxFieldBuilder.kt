package presenters.forms.fields

import presenters.fields.SingleValuedField
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun Fields.checkBox(
    name: String? = null,
    label: String? = name,
    value: Boolean? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((Boolean?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = boolean(name, label, value, isReadonly, isRequired, validator)

inline fun Fields.checkBox(
    property: KProperty<*>,
    label: String? = property.name,
    value: Boolean? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((Boolean?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = checkBox(property.name, label, value, isReadonly, isRequired, validator)