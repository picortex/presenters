package presenters.forms.fields

import presenters.fields.*
import presenters.fields.internal.IntegerInputField
import presenters.fields.internal.NumberBasedValueField
import presenters.forms.Fields
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun Fields.integer(
    name: String? = null,
    label: String? = name,
    hint: String? = label,
    value: Int? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = InputFieldWithValue.DEFAULT_IS_READONLY,
    isRequired: Boolean = InputFieldWithValue.DEFAULT_IS_REQUIRED,
    max: Int? = NumberBasedValueField.DEFAULT_MAX,
    min: Int? = NumberBasedValueField.DEFAULT_MIN,
    step: Int = IntegerInputField.DEFAULT_STEP,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
): ReadOnlyProperty<Fields, NumberBasedValueField<Int>> = getOrCreate { property ->
    IntegerInputField(
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

inline fun Fields.integer(
    property: KProperty<*>,
    label: String? = property.name,
    hint: String? = label,
    value: Int? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = InputFieldWithValue.DEFAULT_IS_READONLY,
    isRequired: Boolean = InputFieldWithValue.DEFAULT_IS_REQUIRED,
    max: Int? = NumberBasedValueField.DEFAULT_MAX,
    min: Int? = NumberBasedValueField.DEFAULT_MIN,
    step: Int = IntegerInputField.DEFAULT_STEP,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = integer(property.name, label, hint, value, isReadonly, isRequired, max, min, step, validator)