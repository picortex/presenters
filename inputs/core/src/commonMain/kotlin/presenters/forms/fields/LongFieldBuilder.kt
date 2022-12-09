package presenters.forms.fields

import presenters.fields.*
import presenters.fields.internal.NumberBasedValueField
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun Fields.long(
    name: String? = null,
    label: String? = name,
    hint: String? = label,
    value: Long? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = InputFieldWithValue.DEFAULT_IS_READONLY,
    isRequired: Boolean = InputFieldWithValue.DEFAULT_IS_REQUIRED,
    max: Long? = NumberBasedValueField.DEFAULT_MAX,
    min: Long? = NumberBasedValueField.DEFAULT_MIN,
    step: Long = LongInputField.DEFAULT_STEP,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = getOrCreate { property ->
    LongInputField(
        name = name ?: property.name,
        label = InputLabel(label ?: property.name, isReadonly),
        hint = hint ?: property.name,
        defaultValue = value?.toString(),
        isReadonly = isReadonly,
        isRequired = isRequired,
        max = max,
        min = min,
        step = step,
        validator = validator,
    )
}

inline fun Fields.long(
    property: KProperty<*>,
    label: String? = property.name,
    hint: String? = label,
    value: Long? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = InputFieldWithValue.DEFAULT_IS_READONLY,
    isRequired: Boolean = InputFieldWithValue.DEFAULT_IS_REQUIRED,
    max: Long? = NumberBasedValueField.DEFAULT_MAX,
    min: Long? = NumberBasedValueField.DEFAULT_MIN,
    step: Long = LongInputField.DEFAULT_STEP,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = long(property.name, label, hint, value, isReadonly, isRequired, max, min, step, validator)