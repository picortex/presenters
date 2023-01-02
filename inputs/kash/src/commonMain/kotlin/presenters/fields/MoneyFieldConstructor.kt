package presenters.fields

import kash.Currency
import kash.Money
import presenters.fields.InputLabel
import presenters.fields.MoneyInputField
import presenters.fields.SingleValuedField
import presenters.fields.internal.MoneyInputFieldImpl
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun MoneyInputField(
    name: String,
    label: String = name,
    hint: String = label,
    selectCurrency: Boolean = false,
    value: Money? = null,
    currency: Currency? = value?.currency,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    max: Double? = null,
    min: Double? = null,
    step: Double? = null
): MoneyInputField = MoneyInputFieldImpl(
    name = name,
    label = InputLabel(label, isRequired),
    hint = hint,
    selectCurrency = selectCurrency,
    currency = currency,
    defaultValue = value,
    isReadonly = isReadonly,
    isRequired = isRequired,
    max = max,
    min = min,
    step = step
)