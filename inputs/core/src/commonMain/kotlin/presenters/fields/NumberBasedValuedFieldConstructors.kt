package presenters.fields

import presenters.fields.internal.AbstractNumberBasedValueField
import presenters.fields.internal.DoubleInputField
import presenters.fields.internal.IntegerInputField
import presenters.fields.internal.LongInputField

inline fun NumberBasedValuedField(
    name: String,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    label: InputLabel = InputLabel(name, isRequired),
    hint: String = label.text,
    defaultValue: String? = SingleValuedField.DEFAULT_VALUE,
    noinline formatter: ((Double?) -> String?)? = null,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    max: Double? = AbstractNumberBasedValueField.DEFAULT_MAX,
    min: Double? = AbstractNumberBasedValueField.DEFAULT_MIN,
    step: Double? = DoubleInputField.DEFAULT_STEP,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
): NumberBasedValuedField<Double> = DoubleInputField(name, isRequired, label, hint, defaultValue, formatter, isReadonly, max, min, step, validator)

inline fun NumberBasedValuedField(
    name: String,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    label: InputLabel = InputLabel(name, isRequired),
    hint: String = label.text,
    defaultValue: String? = SingleValuedField.DEFAULT_VALUE,
    noinline formatter: ((Int?) -> String?)? = null,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    max: Int? = AbstractNumberBasedValueField.DEFAULT_MAX,
    min: Int? = AbstractNumberBasedValueField.DEFAULT_MIN,
    step: Int = IntegerInputField.DEFAULT_STEP,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
): NumberBasedValuedField<Int> = IntegerInputField(name, isRequired, label, hint, defaultValue, formatter, isReadonly, max, min, step, validator)

inline fun NumberBasedValuedField(
    name: String,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    label: InputLabel = InputLabel(name, isRequired),
    hint: String = label.text,
    defaultValue: String? = SingleValuedField.DEFAULT_VALUE,
    noinline formatter: ((Long?) -> String?)? = null,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    max: Long? = AbstractNumberBasedValueField.DEFAULT_MAX,
    min: Long? = AbstractNumberBasedValueField.DEFAULT_MIN,
    step: Long = LongInputField.DEFAULT_STEP,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
): NumberBasedValuedField<Long> = LongInputField(name, isRequired, label, hint, defaultValue, formatter, isReadonly, max, min, step, validator)