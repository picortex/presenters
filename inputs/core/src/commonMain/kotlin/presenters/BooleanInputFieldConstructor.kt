@file:Suppress("NOTHING_TO_INLINE")

package presenters

import presenters.internal.numbers.BooleanInputFieldImpl
import kotlin.reflect.KProperty

inline fun BooleanInputField(
    name: String,
    label: String = name,
    hint: String = label,
    value: Boolean? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((Boolean?) -> Unit)? = null
): BooleanInputField = BooleanInputFieldImpl(
    name = name,
    label = Label(label, isReadonly),
    hint = hint,
    value = value,
    isReadonly = isReadonly,
    isRequired = isRequired,
    validator = validator,
)

inline fun Fields.boolean(
    name: String,
    label: String = name,
    hint: String = label,
    value: Boolean? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((Boolean?) -> Unit)? = null
): BooleanInputField = getOrCreate(name) {
    BooleanInputField(name, label, hint, value, isReadonly, isRequired, validator)
}

inline fun Fields.boolean(
    name: KProperty<Boolean?>,
    label: String = name.name,
    hint: String = label,
    value: Boolean? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((Boolean?) -> Unit)? = null
) = boolean(name.name, label, hint, value, isReadonly, isRequired, validator)