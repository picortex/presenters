@file:Suppress("NOTHING_TO_INLINE")

package presenters

import kash.Currency
import kash.Money
import presenters.internal.MoneyInputFieldImpl
import kotlin.reflect.KProperty

inline fun MoneyInputField(
    name: String,
    isRequired: Boolean = false,
    label: String = name,
    hint: String = label,
    value: Money? = null,
    currency: Currency? = value?.currency,
    formatter: Formatter<Double> = MoneyNumberFormatter,
    selectCurrency: Boolean = true,
    isReadonly: Boolean = false,
    maxAmount: Double? = null,
    minAmount: Double? = null,
    stepAmount: Double? = null,
    noinline validator: ((Money?) -> Unit)? = null
): MoneyInputField = MoneyInputFieldImpl(
    name = name,
    isRequired = isRequired,
    label = Label(label, isRequired),
    hint = hint,
    value = value,
    fixedCurrency = currency,
    selectCurrency = selectCurrency,
    formatter = formatter,
    isReadonly = isReadonly,
    maxAmount = maxAmount,
    minAmount = minAmount,
    stepAmount = stepAmount,
    validator = validator,
)

inline fun Fields.money(
    name: String,
    isRequired: Boolean = false,
    label: String = name,
    hint: String = label,
    value: Money? = null,
    currency: Currency? = value?.currency,
    formatter: Formatter<Double> = MoneyNumberFormatter,
    selectCurrency: Boolean = true,
    isReadonly: Boolean = false,
    maxAmount: Double? = null,
    minAmount: Double? = null,
    stepAmount: Double? = null,
    noinline validator: ((Money?) -> Unit)? = null
): MoneyInputField = getOrCreate(name) {
    MoneyInputField(name, isRequired, label, hint, value, currency, formatter, selectCurrency, isReadonly, maxAmount, minAmount, stepAmount, validator)
}

inline fun Fields.money(
    name: KProperty<*>,
    isRequired: Boolean = false,
    label: String = name.name,
    hint: String = label,
    value: Money? = null,
    currency: Currency? = value?.currency,
    formatter: Formatter<Double> = MoneyNumberFormatter,
    selectCurrency: Boolean = true,
    isReadonly: Boolean = false,
    maxAmount: Double? = null,
    minAmount: Double? = null,
    stepAmount: Double? = null,
    noinline validator: ((Money?) -> Unit)? = null
) = money(name.name, isRequired, label, hint, value, currency, formatter, selectCurrency, isReadonly, maxAmount, minAmount, stepAmount, validator)