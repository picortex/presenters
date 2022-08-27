package presenters.forms.fields

import kash.Currency
import presenters.fields.*
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun Fields.money(
    name: String? = null,
    label: String? = name,
    hint: String? = label,
    selectCurrency: Boolean = false,
    currency: Currency? = null,
    value: String? = null,
    isReadonly: Boolean = InputFieldWithValue.DEFAULT_IS_READONLY,
    isRequired: Boolean = InputFieldWithValue.DEFAULT_IS_REQUIRED,
    noinline validator: (String?) -> String? = { it }
) = getOrCreate { property ->
    MoneyInputField(
        name = name ?: property.name,
        label = label ?: property.name,
        hint = hint ?: property.name,
        selectCurrency = selectCurrency,
        currency = currency,
        value = value,
        isReadonly = isReadonly,
        isRequired = isRequired,
        validator = validator,
    )
}

inline fun Fields.money(
    property: KProperty<*>,
    label: String? = property.name,
    hint: String? = label,
    selectCurrency: Boolean = false,
    currency: Currency? = null,
    value: String? = null,
    isReadonly: Boolean = InputFieldWithValue.DEFAULT_IS_READONLY,
    isRequired: Boolean = InputFieldWithValue.DEFAULT_IS_REQUIRED,
    noinline validator: (String?) -> String? = { it }
) = money(property.name, label, hint, selectCurrency, currency, value, isReadonly, isRequired, validator)