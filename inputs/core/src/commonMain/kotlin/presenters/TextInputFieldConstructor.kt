@file:Suppress("NOTHING_TO_INLINE")

package presenters

import presenters.forms.Fields
import presenters.forms.fields.getOrCreate
import presenters.internal.TextInputFieldImpl
import kotlin.reflect.KProperty

inline fun TextInputField(
    name: String,
    label: String? = name,
    hint: String? = label,
    value: String? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    maxLength: Int? = null,
    minLength: Int? = null,
    noinline validator: ((String?) -> Unit)? = null
): TextInputField = TextInputFieldImpl(
    name = name,
    label = Label(label ?: name, isRequired),
    hint = hint ?: label ?: name,
    value = value,
    isReadonly = isReadonly,
    isRequired = isRequired,
    maxLength = maxLength,
    minLength = minLength,
    validator = validator,
)

inline fun Fields.text(
    name: String,
    label: String = name,
    hint: String = label,
    value: String? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    maxLength: Int? = null,
    minLength: Int? = null,
    noinline validator: ((String?) -> Unit)? = null
): TextInputField = getOrCreate(name) {
    TextInputField(name, label, hint, value, isReadonly, isRequired, maxLength, minLength, validator)
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