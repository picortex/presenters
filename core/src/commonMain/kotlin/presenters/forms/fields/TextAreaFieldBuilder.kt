package presenters.forms.fields

import presenters.fields.TextAreaField
import presenters.fields.SingleValuedField
import presenters.fields.ValuedField
import presenters.fields.internal.TextBasedValueField
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun Fields.textArea(
    name: String? = null,
    label: String? = name,
    hint: String? = label,
    value: String? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    maxLength: Int? = TextBasedValueField.DEFAULT_MAX_LENGTH,
    minLength: Int? = TextBasedValueField.DEFAULT_MIN_LENGTH,
    noinline validator: ((String?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) = getOrCreate { property ->
    TextAreaField(
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

inline fun Fields.textArea(
    property: KProperty<*>,
    label: String? = property.name,
    hint: String? = label,
    value: String? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    maxLength: Int? = TextBasedValueField.DEFAULT_MAX_LENGTH,
    minLength: Int? = TextBasedValueField.DEFAULT_MIN_LENGTH,
    noinline validator: ((String?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) = textArea(property.name, label, hint, value, isReadonly, isRequired, maxLength, minLength, validator)