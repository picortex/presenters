package presenters.forms.fields

import presenters.fields.InputLabel
import presenters.fields.SingleValuedField
import presenters.fields.internal.BooleanBasedValuedFieldImpl
import presenters.forms.Fields
import kotlin.reflect.KProperty

fun Fields.boolean(
    name: String,
    label: String = name,
    value: Boolean? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    validator: ((Boolean?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = getOrCreate(name) {
    BooleanBasedValuedFieldImpl(
        name = name,
        label = InputLabel(label, isReadonly),
        defaultValue = value,
        isReadonly = isReadonly,
        isRequired = isRequired,
        validator = validator,
    )
}

inline fun Fields.boolean(
    name: KProperty<*>,
    label: String = name.name,
    value: Boolean? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((Boolean?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = boolean(name.name, label, value, isReadonly, isRequired, validator)