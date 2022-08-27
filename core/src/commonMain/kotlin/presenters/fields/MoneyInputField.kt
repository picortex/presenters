@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import kash.Currency
import kash.Money
import kotlinx.collections.interoperable.toInteroperableList
import presenters.fields.internal.AbstractTextInputFieldRaw
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.reflect.KProperty

class MoneyInputField(
    override val name: String,
    override val label: String = name,
    override val hint: String = label,
    val selectCurrency: Boolean = false,
    val currency: Currency? = null,
    value: String? = null,
    override val isReadonly: Boolean = InputFieldWithValue.DEFAULT_IS_READONLY,
    override val isRequired: Boolean = InputFieldWithValue.DEFAULT_IS_REQUIRED,
    override val validator: (String?) -> String? = { it }
) : AbstractTextInputFieldRaw(name, label, hint, value, isReadonly, isRequired, validator) {

    @JsName("from_property")
    constructor(
        name: KProperty<*>,
        label: String = name.name,
        hint: String = name.name,
        selectCurrency: Boolean = false,
        currency: Currency? = null,
        value: String? = null,
        isReadonly: Boolean = InputFieldWithValue.DEFAULT_IS_READONLY,
        isRequired: Boolean = InputFieldWithValue.DEFAULT_IS_REQUIRED,
        validator: (String?) -> String? = { it }
    ) : this(name.name, label, hint, selectCurrency, currency, value, isReadonly, isRequired, validator)

    val currencies by lazy {
        DropDownInputField(
            name = "$name-currency",
            label = "Currency",
            isReadonly = !selectCurrency,
            options = Currency.values.map {
                DropDownInputField.Option(
                    label = it.name,
                    value = it.name,
                    selected = it == currency
                )
            }.toInteroperableList()
        )
    }

    val amount by lazy {
        NumberInputField(
            name = "$name-value",
            label = "Value",
            hint = hint,
            value = value,
            isReadonly,
            isRequired,
            validator
        )
    }
}