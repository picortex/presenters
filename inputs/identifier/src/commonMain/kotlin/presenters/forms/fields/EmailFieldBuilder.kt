package presenters.forms.fields

import identifier.Email
import identifier.serializers.EmailSerializer
import kotlinx.serialization.builtins.serializer
import presenters.fields.InputLabel
import presenters.fields.ValuedField
import presenters.fields.internal.TextBasedValueFieldImpl
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun Fields.email(
    name: String? = null,
    label: String? = name,
    hint: String? = label,
    value: String? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    maxLength: Int? = TextBasedValueFieldImpl.DEFAULT_MAX_LENGTH,
    minLength: Int? = TextBasedValueFieldImpl.DEFAULT_MIN_LENGTH,
    noinline validator: ((String?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) = getOrCreate { property ->
    TextBasedValueFieldImpl(
        name = name ?: property.name,
        label = InputLabel(name ?: property.name, isRequired),
        hint = hint ?: property.name,
        defaultText = value,
        isReadonly = isReadonly,
        isRequired = isRequired,
        serializer = EmailSerializer,
        maxLength = maxLength,
        minLength = minLength,
        transformer = { text -> text?.let { Email(it) } },
        validator = { },
    )
}

inline fun Fields.email(
    name: KProperty<*>,
    label: String? = name.name,
    hint: String? = label,
    value: String? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    maxLength: Int? = TextBasedValueFieldImpl.DEFAULT_MAX_LENGTH,
    minLength: Int? = TextBasedValueFieldImpl.DEFAULT_MIN_LENGTH,
    noinline validator: ((String?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) = email(name.name, label, hint, value, isReadonly, isRequired, maxLength, minLength, validator)