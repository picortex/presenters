package presenters.form.fields

import geo.GeoLocation
import presenters.fields.LocationInputField
import presenters.fields.*
import presenters.forms.Fields
import presenters.forms.fields.getOrCreate
import kotlin.reflect.KProperty

fun Fields.location(
    name: String,
    label: String = name,
    hint: String? = label,
    value: GeoLocation? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = getOrCreate(name) {
    LocationInputField(
        name = name,
        label = InputLabel(label, isRequired),
        hint = hint ?: label,
        defaultValue = value?.address,
        isReadonly = isReadonly,
        isRequired = isRequired,
        validator = validator
    )
}

inline fun Fields.location(
    name: KProperty<*>,
    label: String = name.name,
    hint: String? = label,
    value: GeoLocation? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) = location(name.name, label, hint, value, isReadonly, isRequired, validator)