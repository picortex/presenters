@file:Suppress("NOTHING_TO_INLINE")

package presenters

import presenters.forms.Fields
import presenters.forms.fields.getOrCreate
import presenters.internal.numbers.BooleanInputFieldImpl
import kotlin.reflect.KProperty

inline fun BooleanInputField(
    name: String,
    label: String = name,
    value: Boolean? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((Boolean?) -> Unit)? = null
): BooleanInputField = BooleanInputFieldImpl(
    name = name,
    label = Label(label, isReadonly),
    value = value,
    isReadonly = isReadonly,
    isRequired = isRequired,
    validator = validator,
)

fun Fields.boolean(
    name: String,
    label: String = name,
    value: Boolean? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    validator: ((Boolean?) -> Unit)? = null
): BooleanInputField = getOrCreate(name) {
    BooleanInputField(name, label, value, isReadonly, isRequired, validator)
}

inline fun Fields.boolean(
    name: KProperty<*>,
    label: String = name.name,
    value: Boolean? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((Boolean?) -> Unit)? = null
) = boolean(name.name, label, value, isReadonly, isRequired, validator)