package presenters.forms.fields

import kash.Currency
import kash.Money
import presenters.Label
import presenters.fields.MoneyInputField
import presenters.fields.SingleValuedField
import presenters.fields.internal.MoneyInputFieldImpl
import presenters.forms.Fields
import kotlin.reflect.KProperty

fun Fields.money(
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
): MoneyInputField = getOrCreate(name) {
    MoneyInputFieldImpl(
        name = name,
        label = Label(label, isRequired),
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
}

inline fun Fields.money(
    name: KProperty<*>,
    label: String = name.name,
    hint: String = label,
    selectCurrency: Boolean = false,
    value: Money? = null,
    currency: Currency? = value?.currency,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    max: Double? = null,
    min: Double? = null,
    step: Double? = null
) = money(name.name, label, hint, selectCurrency, value, currency, isReadonly, isRequired, max, min, step)