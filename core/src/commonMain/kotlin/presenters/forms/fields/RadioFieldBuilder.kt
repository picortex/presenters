package presenters.forms.fields

import presenters.fields.RadioInputField
import presenters.fields.ValuedField
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun Fields.radio(
    name: String? = null,
    label: String? = name,
    value: Boolean? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((Boolean?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) = getOrCreate { property ->
    RadioInputField(
        name = name ?: property.name,
        label = label ?: property.name,
        defaultValue = value,
        isReadonly = isReadonly,
        isRequired = isRequired,
        validator = validator,
    )
}

inline fun Fields.radio(
    property: KProperty<*>,
    label: String? = property.name,
    value: Boolean? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((Boolean?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) = radio(property.name, label, value, isReadonly, isRequired, validator)