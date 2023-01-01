package presenters.forms.fields

import presenters.fields.InputLabel
import presenters.fields.SingleValuedField
import presenters.fields.internal.IntegerInputField
import presenters.fields.internal.NumberBasedValueField
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun Fields.integer(
    name: String,
    label: String = name,
    hint: String? = label,
    value: Int? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    max: Int? = NumberBasedValueField.DEFAULT_MAX,
    min: Int? = NumberBasedValueField.DEFAULT_MIN,
    step: Int = IntegerInputField.DEFAULT_STEP,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
): NumberBasedValueField<Int> = getOrCreate(name) {
    IntegerInputField(
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

inline fun Fields.integer(
    property: KProperty<*>,
    label: String = property.name,
    hint: String? = label,
    value: Int? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    max: Int? = NumberBasedValueField.DEFAULT_MAX,
    min: Int? = NumberBasedValueField.DEFAULT_MIN,
    step: Int = IntegerInputField.DEFAULT_STEP,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = integer(property.name, label, hint, value, isReadonly, isRequired, max, min, step, validator)