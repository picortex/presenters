package presenters.forms.fields

import presenters.fields.Formatter
import presenters.Label
import presenters.fields.NumberBasedValuedField
import presenters.fields.SingleValuedField
import presenters.fields.internal.DoubleInputField
import presenters.fields.internal.AbstractNumberBasedValueField
import presenters.forms.Fields
import kotlin.reflect.KProperty

fun Fields.double(
    name: String,
    label: String = name,
    hint: String? = label,
    value: Double? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    formatter: Formatter<Double>? = null,
    max: Double? = AbstractNumberBasedValueField.DEFAULT_MAX,
    min: Double? = AbstractNumberBasedValueField.DEFAULT_MIN,
    step: Double = DoubleInputField.DEFAULT_STEP,
    validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
): NumberBasedValuedField<Double> = getOrCreate(name) {
    DoubleInputField(
        name = name,
        label = Label(label, isReadonly),
        hint = hint ?: name,
        defaultValue = value?.toString(),
        formatter = formatter,
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
    formatter: Formatter<Double>? = null,
    max: Double? = AbstractNumberBasedValueField.DEFAULT_MAX,
    min: Double? = AbstractNumberBasedValueField.DEFAULT_MIN,
    step: Double = DoubleInputField.DEFAULT_STEP,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = double(property.name, label, hint, value, isReadonly, isRequired, formatter, max, min, step, validator)