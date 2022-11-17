package presenters.forms.fields

import presenters.fields.TextInputField
import presenters.fields.ValuedField
import presenters.fields.internal.TextBasedValueField
import presenters.forms.Fields
import kotlin.reflect.KProperty

@PublishedApi
internal const val DEFAULT_MIN_LENGTH = 2

inline fun Fields.name(
    name: String? = null,
    label: String? = name,
    hint: String? = label,
    value: String? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    maxLength: Int? = TextBasedValueField.DEFAULT_MAX_LENGTH,
    minLength: Int? = DEFAULT_MIN_LENGTH,
    noinline validator: ((String?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) = getOrCreate { property ->
    TextInputField(
        name = name ?: property.name,
        label = label ?: property.name,
        hint = hint ?: property.name,
        defaultValue = value,
        isReadonly = isReadonly,
        isRequired = isRequired,
        maxLength = maxLength,
        minLength = minLength,
        validator = validator,
    )
}

inline fun Fields.name(
    name: KProperty<*>,
    label: String? = name.name,
    hint: String? = label,
    value: String? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    maxLength: Int? = TextBasedValueField.DEFAULT_MAX_LENGTH,
    minLength: Int? = DEFAULT_MIN_LENGTH,
    noinline validator: ((String?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) = this.name(name.name, label, hint, value, isReadonly, isRequired, maxLength, minLength, validator)