package presenters.forms.fields

import presenters.fields.CheckBoxInputField
import presenters.fields.SingleValuedField
import presenters.fields.ValuedField
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun Fields.checkBox(
    name: String? = null,
    label: String? = name,
    value: Boolean? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((Boolean?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) = getOrCreate { property ->
    CheckBoxInputField(
        name = name ?: property.name,
        label = label ?: property.name,
        defaultValue = value,
        isReadonly = isReadonly,
        isRequired = isRequired,
        validator = validator,
    )
}

inline fun Fields.checkBox(
    property: KProperty<*>,
    label: String? = property.name,
    value: Boolean? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((Boolean?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) = checkBox(property.name, label, value, isReadonly, isRequired, validator)