package presenters.forms.fields

import kotlinx.serialization.builtins.serializer
import presenters.fields.InputLabel
import presenters.fields.SingleValuedField
import presenters.fields.internal.TextBasedValueFieldImpl
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun Fields.password(
    name: String? = null,
    label: String? = name,
    hint: String? = label,
    value: String? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    maxLength: Int? = TextBasedValueFieldImpl.DEFAULT_MAX_LENGTH,
    minLength: Int? = TextBasedValueFieldImpl.DEFAULT_MIN_LENGTH,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = getOrCreate { property ->
    TextBasedValueFieldImpl(
        name = name ?: property.name,
        label = InputLabel(label ?: property.name, isRequired),
        hint = hint ?: property.name,
        defaultText = value,
        isReadonly = isReadonly,
        isRequired = isRequired,
        serializer = String.serializer(),
        maxLength = maxLength,
        minLength = minLength,
        transformer = { it },
        validator = validator,
    )
}

inline fun Fields.password(
    property: KProperty<*>,
    label: String? = property.name,
    hint: String? = label,
    value: String? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    maxLength: Int? = TextBasedValueFieldImpl.DEFAULT_MAX_LENGTH,
    minLength: Int? = TextBasedValueFieldImpl.DEFAULT_MIN_LENGTH,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = password(property.name, label, hint, value, isReadonly, isRequired, maxLength, minLength, validator)