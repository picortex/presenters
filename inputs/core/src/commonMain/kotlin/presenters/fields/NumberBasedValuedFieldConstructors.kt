package presenters.fields

import presenters.Label
import presenters.NumberInputField
import presenters.internal.DoubleInputFieldImpl
import presenters.internal.IntegerInputFieldImpl
import presenters.internal.LongInputFieldImpl

inline fun DoubleValuedField(
    name: String,
    isRequired: Boolean = false,
    label: Label = Label(name, isRequired),
    hint: String = label.text,
    value: Double? = null,
    formatter: Formatter<Double>? = null,
    isReadonly: Boolean = false,
    max: Double? = null,
    min: Double? = null,
    step: Double? = null,
    noinline validator: ((Double?) -> Unit)? = null
): NumberInputField<Double> = DoubleInputFieldImpl(name, isRequired, label, hint, isReadonly, max, min, step, formatter, value, validator)

inline fun IntegerValuedField(
    name: String,
    isRequired: Boolean = false,
    label: Label = Label(name, isRequired),
    hint: String = label.text,
    value: Int? = null,
    formatter: Formatter<Int>? = null,
    isReadonly: Boolean = false,
    max: Int? = null,
    min: Int? = null,
    step: Int? = null,
    noinline validator: ((Int?) -> Unit)? = null
): NumberInputField<Int> = IntegerInputFieldImpl(name, isRequired, label, hint, isReadonly, max, min, step, formatter, value, validator)

inline fun LongValuedField(
    name: String,
    isRequired: Boolean = false,
    label: Label = Label(name, isRequired),
    hint: String = label.text,
    value: Long? = null,
    formatter: Formatter<Long>? = null,
    isReadonly: Boolean = false,
    max: Long? = null,
    min: Long? = null,
    step: Long? = null,
    noinline validator: ((Long?) -> Unit)? = null
): NumberInputField<Long> = LongInputFieldImpl(name, isRequired, label, hint, isReadonly, max, min, step, formatter, value, validator)