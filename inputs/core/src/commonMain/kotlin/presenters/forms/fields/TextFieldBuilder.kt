package presenters.forms.fields

import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import presenters.fields.SingleValuedField
import presenters.fields.internal.TextBasedValueFieldImpl
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun Fields.text(
    name: String? = null,
    label: String? = name,
    hint: String? = label,
    value: String? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    maxLength: Int? = TextBasedValueFieldImpl.DEFAULT_MAX_LENGTH,
    minLength: Int? = TextBasedValueFieldImpl.DEFAULT_MIN_LENGTH,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = textTo(name, label, hint, value, isReadonly, isRequired, maxLength, minLength, String.serializer().nullable, validator) { it }

inline fun Fields.text(
    name: KProperty<*>,
    label: String? = name.name,
    hint: String? = label,
    value: String? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    maxLength: Int? = TextBasedValueFieldImpl.DEFAULT_MAX_LENGTH,
    minLength: Int? = TextBasedValueFieldImpl.DEFAULT_MIN_LENGTH,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = text(name.name, label, hint, value, isReadonly, isRequired, maxLength, minLength, validator)