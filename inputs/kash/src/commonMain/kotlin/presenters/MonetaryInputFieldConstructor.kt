@file:Suppress("NOTHING_TO_INLINE")

package presenters

import formatter.NumberFormatter
import kash.Monetary
import kash.MoneyFormatter
import presenters.internal.DEFAULT_FORMATTER
import presenters.internal.MonetaryInputFieldImpl
import kotlin.reflect.KProperty

inline fun MonetaryInputField(
    name: String,
    isRequired: Boolean = false,
    label: String = name,
    hint: String = label,
    value: Monetary? = null,
    formatter: NumberFormatter? = DEFAULT_FORMATTER,
    isReadonly: Boolean = false,
    maxAmount: Monetary? = null,
    minAmount: Monetary? = null,
    stepAmount: Double? = null,
    noinline validator: ((Monetary?) -> Unit)? = null
): MonetaryInputField = MonetaryInputFieldImpl(
    name = name,
    isRequired = isRequired,
    label = Label(label, isRequired),
    hint = hint,
    value = value,
    formatter = formatter,
    isReadonly = isReadonly,
    maxAmount = maxAmount,
    minAmount = minAmount,
    stepAmount = stepAmount,
    validator = validator,
)

inline fun Fields.monetary(
    name: String,
    isRequired: Boolean = false,
    label: String = name,
    hint: String = label,
    value: Monetary? = null,
    formatter: NumberFormatter? = DEFAULT_FORMATTER,
    isReadonly: Boolean = false,
    maxAmount: Monetary? = null,
    minAmount: Monetary? = null,
    stepAmount: Double? = null,
    noinline validator: ((Monetary?) -> Unit)? = null
): MonetaryInputField = getOrCreate(name) {
    MonetaryInputField(name, isRequired, label, hint, value, formatter, isReadonly, maxAmount, minAmount, stepAmount, validator)
}

inline fun Fields.monetary(
    name: KProperty<Monetary?>,
    isRequired: Boolean = false,
    label: String = name.name,
    hint: String = label,
    value: Monetary? = null,
    formatter: NumberFormatter? = DEFAULT_FORMATTER,
    isReadonly: Boolean = false,
    maxAmount: Monetary? = null,
    minAmount: Monetary? = null,
    stepAmount: Double? = null,
    noinline validator: ((Monetary?) -> Unit)? = null
) = monetary(name.name, isRequired, label, hint, value, formatter, isReadonly, maxAmount, minAmount, stepAmount, validator)