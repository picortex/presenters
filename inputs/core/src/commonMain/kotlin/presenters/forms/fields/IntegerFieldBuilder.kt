package presenters.forms.fields

import presenters.fields.Formatter
import presenters.fields.InputLabel
import presenters.fields.NumberBasedValuedField
import presenters.fields.SingleValuedField
import presenters.fields.internal.IntegerInputField
import presenters.fields.internal.AbstractNumberBasedValueField
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun Fields.integer(
    name: String,
    label: String = name,
    hint: String? = label,
    value: Int? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    formatter: Formatter<Int>? = null,
    max: Int? = AbstractNumberBasedValueField.DEFAULT_MAX,
    min: Int? = AbstractNumberBasedValueField.DEFAULT_MIN,
    step: Int = IntegerInputField.DEFAULT_STEP,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
): NumberBasedValuedField<Int> = getOrCreate(name) {
    IntegerInputField(
        name = name,
        label = InputLabel(label, isReadonly),
        hint = hint ?: name,
        defaultValue = value?.toString(),
        isReadonly = isReadonly,
        isRequired = isRequired,
        formatter = formatter,
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
    formatter: Formatter<Int>? = null,
    max: Int? = AbstractNumberBasedValueField.DEFAULT_MAX,
    min: Int? = AbstractNumberBasedValueField.DEFAULT_MIN,
    step: Int = IntegerInputField.DEFAULT_STEP,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = integer(property.name, label, hint, value, isReadonly, isRequired, formatter, max, min, step, validator)