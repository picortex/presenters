package presenters.form.fields

import geo.GeoLocation
import presenters.field.LocationInputField
import presenters.fields.*
import presenters.forms.Fields
import presenters.forms.fields.getOrCreate
import kotlin.reflect.KProperty

inline fun Fields.location(
    name: String? = null,
    label: String? = name,
    hint: String? = label,
    value: GeoLocation? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((GeoLocation?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) = getOrCreate { property ->
    LocationInputField(
        name = name ?: property.name,
        label = label ?: property.name,
        hint = hint ?: property.name,
        defaultValue = value,
        isReadonly = isReadonly,
        isRequired = isRequired,
        validator = validator
    )
}

inline fun Fields.location(
    name: KProperty<*>,
    label: String? = name.name,
    hint: String? = label,
    value: GeoLocation? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    noinline validator: ((GeoLocation?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) = location(name.name, label, hint, value, isReadonly, isRequired, validator)