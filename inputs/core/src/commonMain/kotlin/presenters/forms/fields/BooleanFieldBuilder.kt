package presenters.forms.fields

import presenters.fields.BooleanBasedInputField
import presenters.fields.InputLabel
import presenters.fields.SingleValuedField
import presenters.fields.internal.BooleanBasedInputFieldImpl
import presenters.forms.Fields
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun Fields.boolean(
    name: String? = null,
    label: String? = name,
    value: Boolean? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((Boolean?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
): ReadOnlyProperty<Fields, BooleanBasedInputField> = getOrCreate { property ->
    BooleanBasedInputFieldImpl(
        name = name ?: property.name,
        label = InputLabel(label ?: property.name, isReadonly),
        defaultValue = value,
        isReadonly = isReadonly,
        isRequired = isRequired,
        validator = validator,
    )
}

inline fun Fields.boolean(
    property: KProperty<*>,
    label: String? = property.name,
    value: Boolean? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((Boolean?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = checkBox(property.name, label, value, isReadonly, isRequired, validator)