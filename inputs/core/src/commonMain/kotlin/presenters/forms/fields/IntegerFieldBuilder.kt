package presenters.forms.fields

import presenters.fields.*
import presenters.fields.internal.NumberBasedValueField
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun Fields.integer(
    name: String? = null,
    label: String? = name,
    hint: String? = label,
    value: Int? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = InputFieldWithValue.DEFAULT_IS_READONLY,
    isRequired: Boolean = InputFieldWithValue.DEFAULT_IS_REQUIRED,
    max: Int? = NumberBasedValueField.DEFAULT_MAX,
    min: Int? = NumberBasedValueField.DEFAULT_MIN,
    step: Int = IntegerInputField.DEFAULT_STEP,
    noinline validator: ((Int?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) = getOrCreate { property ->
    IntegerInputField(
        name = name ?: property.name,
        label = label ?: property.name,
        hint = hint ?: property.name,
        defaultValue = value,
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
    value: Int? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = InputFieldWithValue.DEFAULT_IS_READONLY,
    isRequired: Boolean = InputFieldWithValue.DEFAULT_IS_REQUIRED,
    max: Int? = NumberBasedValueField.DEFAULT_MAX,
    min: Int? = NumberBasedValueField.DEFAULT_MIN,
    step: Int = IntegerInputField.DEFAULT_STEP,
    noinline validator: ((Int?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) = integer(property.name, label, hint, value, isReadonly, isRequired, max, min, step, validator)