package presenters.forms.fields

import identifier.Phone
import identifier.serializers.PhoneSerializer
import presenters.fields.InputLabel
import presenters.fields.PHONE_DEFAULT_MAX_LENGTH
import presenters.fields.ValuedField
import presenters.fields.internal.TextBasedValueFieldImpl
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun Fields.phone(
    name: String? = null,
    label: String? = name,
    hint: String? = label,
    value: String? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    maxLength: Int? = PHONE_DEFAULT_MAX_LENGTH,
    minLength: Int? = TextBasedValueFieldImpl.DEFAULT_MIN_LENGTH,
    noinline validator: ((String?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) = getOrCreate { property ->
    TextBasedValueFieldImpl(
        name = name ?: property.name,
        label = InputLabel(label ?: property.name, isRequired),
        hint = hint ?: property.name,
        defaultText = value,
        isReadonly = isReadonly,
        isRequired = isRequired,
        serializer = PhoneSerializer,
        maxLength = maxLength,
        minLength = minLength,
        transformer = { text -> text?.let { Phone(it) } },
        validator = validator,
    )
}

inline fun Fields.phone(
    name: KProperty<*>,
    label: String? = name.name,
    hint: String? = label,
    value: String? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    maxLength: Int? = PHONE_DEFAULT_MAX_LENGTH,
    minLength: Int? = TextBasedValueFieldImpl.DEFAULT_MIN_LENGTH,
    noinline validator: ((String?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) = phone(name.name, label, hint, value, isReadonly, isRequired, maxLength, minLength, validator)