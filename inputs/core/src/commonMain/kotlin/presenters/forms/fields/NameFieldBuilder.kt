package presenters.forms.fields

import presenters.forms.Fields
import kotlin.reflect.KProperty

@PublishedApi
internal const val DEFAULT_MIN_LENGTH = 2

@PublishedApi
internal const val DEFAULT_IS_REQUIRED: Boolean = true

inline fun Fields.name(
    name: String = "name",
    label: String = name,
    hint: String = label,
    value: String? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = DEFAULT_IS_REQUIRED,
    maxLength: Int? = null,
    minLength: Int? = null,
    noinline validator: ((String?) -> Unit)? = null
) = text(name, label, hint, value, isReadonly, isRequired, maxLength, minLength, validator)

inline fun Fields.name(
    name: KProperty<*>,
    label: String = name.name,
    hint: String = label,
    value: String? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = DEFAULT_IS_REQUIRED,
    maxLength: Int? = null,
    minLength: Int? = null,
    noinline validator: ((String?) -> Unit)? = null
) = this.name(name.name, label, hint, value, isReadonly, isRequired, maxLength, minLength, validator)