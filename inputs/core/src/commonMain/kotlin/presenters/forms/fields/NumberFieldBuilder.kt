package presenters.forms.fields

import presenters.fields.SingleValuedField
import presenters.fields.internal.DoubleInputField
import presenters.fields.internal.NumberBasedValueField
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun Fields.number(
    name: String,
    label: String = name,
    hint: String? = label,
    value: Double? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    max: Double? = NumberBasedValueField.DEFAULT_MAX,
    min: Double? = NumberBasedValueField.DEFAULT_MIN,
    step: Double = DoubleInputField.DEFAULT_STEP,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = double(name, label, hint, value, isReadonly, isRequired, max, min, step, validator)

inline fun Fields.number(
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
) = number(property.name, label, hint, value, isReadonly, isRequired, max, min, step, validator)