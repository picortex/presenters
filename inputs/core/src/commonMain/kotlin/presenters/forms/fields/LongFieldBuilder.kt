package presenters.forms.fields

import presenters.fields.Formatter
import presenters.fields.InputLabel
import presenters.fields.NumberBasedValuedField
import presenters.fields.SingleValuedField
import presenters.fields.internal.LongInputField
import presenters.fields.internal.AbstractNumberBasedValueField
import presenters.forms.Fields
import kotlin.reflect.KProperty

fun Fields.long(
    name: String,
    label: String = name,
    hint: String? = label,
    value: Long? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    formatter: Formatter<Long>? = null,
    max: Long? = AbstractNumberBasedValueField.DEFAULT_MAX,
    min: Long? = AbstractNumberBasedValueField.DEFAULT_MIN,
    step: Long = LongInputField.DEFAULT_STEP,
    validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
): NumberBasedValuedField<Long> = getOrCreate(name) {
    LongInputField(
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

inline fun Fields.long(
    property: KProperty<*>,
    label: String = property.name,
    hint: String? = label,
    value: Long? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    formatter: Formatter<Long>? = null,
    max: Long? = AbstractNumberBasedValueField.DEFAULT_MAX,
    min: Long? = AbstractNumberBasedValueField.DEFAULT_MIN,
    step: Long = LongInputField.DEFAULT_STEP,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = long(property.name, label, hint, value, isReadonly, isRequired, formatter, max, min, step, validator)