package presenters.forms.fields

import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import presenters.fields.InputLabel
import presenters.fields.ValuedField
import presenters.fields.internal.TextBasedValueFieldImpl
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun <reified T> Fields.textTo(
    name: String? = null,
    label: String? = name,
    hint: String? = label,
    value: String? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    maxLength: Int? = TextBasedValueFieldImpl.DEFAULT_MAX_LENGTH,
    minLength: Int? = TextBasedValueFieldImpl.DEFAULT_MIN_LENGTH,
    serializer: KSerializer<T> = serializer(),
    noinline validator: ((T?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR,
    noinline transformer: (String?) -> T
) = getOrCreate { property ->
    TextBasedValueFieldImpl(
        name = name ?: property.name,
        label = InputLabel(label ?: name ?: property.name, isRequired),
        hint = hint ?: property.name,
        defaultText = value,
        transformer = transformer,
        serializer = serializer,
        isReadonly = isReadonly,
        isRequired = isRequired,
        maxLength = maxLength,
        minLength = minLength,
        validator = validator,
    )
}

inline fun <reified T> Fields.textTo(
    name: KProperty<*>,
    label: String? = name.name,
    hint: String? = label,
    value: String? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    maxLength: Int? = TextBasedValueFieldImpl.DEFAULT_MAX_LENGTH,
    minLength: Int? = TextBasedValueFieldImpl.DEFAULT_MIN_LENGTH,
    serializer: KSerializer<T> = serializer(),
    noinline validator: ((T?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR,
    noinline transformer: (String?) -> T
) = textTo(name.name, label, hint, value, isReadonly, isRequired, maxLength, minLength, serializer, validator, transformer)