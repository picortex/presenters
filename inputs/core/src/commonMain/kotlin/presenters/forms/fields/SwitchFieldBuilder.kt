package presenters.forms.fields

import presenters.fields.InputLabel
import presenters.fields.SwitchInputField
import presenters.fields.ValuedField
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun Fields.switch(
    name: String? = null,
    label: String? = name,
    value: Boolean? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((Boolean?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) = getOrCreate { property ->
    SwitchInputField(
        name = name ?: property.name,
        label = InputLabel(label ?: property.name,isReadonly),
        defaultValue = value,
        isReadonly = isReadonly,
        isRequired = isRequired,
        validator = validator,
    )
}

inline fun Fields.switch(
    name: KProperty<*>,
    label: String? = name.name,
    value: Boolean? = null,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((Boolean?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) = switch(name.name, label, value, isReadonly, isRequired, validator)