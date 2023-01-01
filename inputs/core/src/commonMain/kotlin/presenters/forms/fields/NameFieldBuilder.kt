package presenters.forms.fields

import kotlinx.serialization.builtins.serializer
import presenters.fields.SingleValuedField
import presenters.fields.TextBasedValuedField
import presenters.fields.internal.TextBasedValuedFieldImpl
import presenters.forms.Fields
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@PublishedApi
internal const val DEFAULT_MIN_LENGTH = 2

@PublishedApi
internal const val DEFAULT_IS_REQUIRED: Boolean = true

inline fun Fields.name(
    name: String = "name",
    label: String = name,
    hint: String? = label,
    value: String? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = DEFAULT_IS_REQUIRED,
    maxLength: Int? = TextBasedValuedFieldImpl.DEFAULT_MAX_LENGTH,
    minLength: Int? = DEFAULT_MIN_LENGTH,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = textTo(name, label, hint, value, isReadonly, isRequired, maxLength, minLength, String.serializer(), validator) { it }

inline fun Fields.name(
    name: KProperty<*>,
    label: String = name.name,
    hint: String? = label,
    value: String? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = DEFAULT_IS_REQUIRED,
    maxLength: Int? = TextBasedValuedFieldImpl.DEFAULT_MAX_LENGTH,
    minLength: Int? = DEFAULT_MIN_LENGTH,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = this.name(name.name, label, hint, value, isReadonly, isRequired, maxLength, minLength, validator)