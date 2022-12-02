package presenters.forms.fields

import presenters.fields.*
import presenters.fields.internal.NumberBasedValueField
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun Fields.number(
    name: String? = null,
    label: String? = name,
    hint: String? = label,
    value: Double? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    max: Double? = NumberBasedValueField.DEFAULT_MAX,
    min: Double? = NumberBasedValueField.DEFAULT_MIN,
    step: Double = DoubleInputField.DEFAULT_STEP,
    noinline validator: ((Double?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) = getOrCreate { property ->
    DoubleInputField(
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

inline fun Fields.number(
    property: KProperty<*>,
    label: String? = property.name,
    hint: String? = label,
    value: Double? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = InputFieldWithValue.DEFAULT_IS_READONLY,
    isRequired: Boolean = InputFieldWithValue.DEFAULT_IS_REQUIRED,
    max: Double? = NumberBasedValueField.DEFAULT_MAX,
    min: Double? = NumberBasedValueField.DEFAULT_MIN,
    step: Double = DoubleInputField.DEFAULT_STEP,
    noinline validator: ((Double?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) = number(property.name, label, hint, value, isReadonly, isRequired, max, min, step, validator)