package presenters.forms.fields

import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import presenters.fields.InputLabel
import presenters.fields.SingleValuedField
import presenters.fields.TextBasedValuedField
import presenters.fields.internal.TextBasedValuedFieldImpl
import presenters.forms.Fields
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : Any> Fields.textTo(
    name: String? = null,
    label: String? = name,
    hint: String? = label,
    value: String? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    maxLength: Int? = TextBasedValuedFieldImpl.DEFAULT_MAX_LENGTH,
    minLength: Int? = TextBasedValuedFieldImpl.DEFAULT_MIN_LENGTH,
    serializer: KSerializer<T> = serializer(),
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR,
    noinline transformer: (String?) -> T?
): ReadOnlyProperty<Fields, TextBasedValuedField<T>> = getOrCreate { property ->
    TextBasedValuedFieldImpl(
        name = name ?: property.name,
        label = InputLabel(label ?: name ?: property.name, isRequired),
        hint = hint ?: property.name,
        defaultValue = value,
        transformer = transformer,
        serializer = serializer,
        isReadonly = isReadonly,
        isRequired = isRequired,
        maxLength = maxLength,
        minLength = minLength,
        validator = validator,
    )
}

inline fun <reified T : Any> Fields.textTo(
    name: KProperty<*>,
    label: String? = name.name,
    hint: String? = label,
    value: String? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    maxLength: Int? = TextBasedValuedFieldImpl.DEFAULT_MAX_LENGTH,
    minLength: Int? = TextBasedValuedFieldImpl.DEFAULT_MIN_LENGTH,
    serializer: KSerializer<T> = serializer(),
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR,
    noinline transformer: (String?) -> T?
) = textTo(name.name, label, hint, value, isReadonly, isRequired, maxLength, minLength, serializer, validator, transformer)