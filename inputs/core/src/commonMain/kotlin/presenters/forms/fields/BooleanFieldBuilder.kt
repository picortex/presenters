package presenters.forms.fields

import presenters.Label
import presenters.fields.BooleanInputField
import presenters.forms.Fields
import presenters.internal.BooleanInputFieldImpl
import kotlin.reflect.KProperty

fun Fields.boolean(
    name: String,
    label: String = name,
    value: Boolean? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    validator: ((Boolean?) -> Unit)? = null
): BooleanInputField = getOrCreate(name) {
    BooleanInputFieldImpl(
        name = name,
        label = Label(label, isReadonly),
        value = value,
        isReadonly = isReadonly,
        isRequired = isRequired,
        validator = validator,
    )
}

inline fun Fields.boolean(
    name: KProperty<*>,
    label: String = name.name,
    value: Boolean? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((Boolean?) -> Unit)? = null
) = boolean(name.name, label, value, isReadonly, isRequired, validator)