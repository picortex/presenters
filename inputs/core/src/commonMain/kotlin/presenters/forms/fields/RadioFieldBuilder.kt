package presenters.forms.fields

import presenters.fields.InputLabel
import presenters.fields.RadioInputField
import presenters.fields.SingleValuedField
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun Fields.radio(
    name: String? = null,
    label: String? = name,
    value: Boolean? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((Boolean?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = getOrCreate { property ->
    RadioInputField(
        name = name ?: property.name,
        label = InputLabel(label ?: property.name,isReadonly),
        defaultValue = value,
        isReadonly = isReadonly,
        isRequired = isRequired,
        validator = validator,
    )
}

inline fun Fields.radio(
    property: KProperty<*>,
    label: String? = property.name,
    value: Boolean? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((Boolean?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = radio(property.name, label, value, isReadonly, isRequired, validator)