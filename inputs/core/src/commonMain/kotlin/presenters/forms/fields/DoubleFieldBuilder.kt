package presenters.forms.fields

import presenters.fields.InputLabel
import presenters.fields.SingleValuedField
import presenters.fields.internal.DoubleInputField
import presenters.fields.internal.NumberBasedValueField
import presenters.forms.Fields
import kotlin.reflect.KProperty

fun Fields.double(
    name: String,
    label: String = name,
    hint: String? = label,
    value: Double? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    max: Double? = NumberBasedValueField.DEFAULT_MAX,
    min: Double? = NumberBasedValueField.DEFAULT_MIN,
    step: Double = DoubleInputField.DEFAULT_STEP,
    validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
): NumberBasedValueField<Double> = getOrCreate(name) {
    DoubleInputField(
        name = name,
        label = InputLabel(label, isReadonly),
        hint = hint ?: name,
        defaultValue = value?.toString(),
        isReadonly = isReadonly,
        isRequired = isRequired,
        max = max,
        min = min,
        step = step,
        validator = validator,
    )
}

inline fun Fields.double(
    property: KProperty<*>,
    label: String = property.name,
    hint: String? = label,
    value: Double? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    max: Double? = NumberBasedValueField.DEFAULT_MAX,
    min: Double? = NumberBasedValueField.DEFAULT_MIN,
    step: Double = DoubleInputField.DEFAULT_STEP,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = double(property.name, label, hint, value, isReadonly, isRequired, max, min, step, validator)