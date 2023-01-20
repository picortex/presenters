package presenters.forms.fields

import presenters.Label
import presenters.TextInputField
import presenters.forms.Fields
import presenters.internal.TextInputFieldImpl
import kotlin.reflect.KProperty

fun Fields.text(
    name: String,
    label: String = name,
    hint: String = label,
    value: String? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    maxLength: Int? = null,
    minLength: Int? = null,
    validator: ((String?) -> Unit)? = null
): TextInputField = getOrCreate(name) {
    TextInputFieldImpl(
        name = name,
        isRequired = isRequired,
        label = Label(label, isRequired),
        hint = hint,
        isReadonly = isReadonly,
        maxLength = maxLength,
        minLength = minLength,
        value = value,
        validator = validator
    )
}

inline fun Fields.text(
    name: KProperty<*>,
    label: String = name.name,
    hint: String = label,
    value: String? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    maxLength: Int? = null,
    minLength: Int? = null,
    noinline validator: ((String?) -> Unit)? = null
) = text(name.name, label, hint, value, isReadonly, isRequired, maxLength, minLength, validator)