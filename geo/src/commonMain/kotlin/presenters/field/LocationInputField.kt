@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.field

import geo.GeoLocation
import kotlinx.serialization.SerializationStrategy
import presenters.fields.SingleValuedField
import presenters.fields.ValuedField
import presenters.fields.internal.AbstractSingleValuedField
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.reflect.KProperty

@JsExport
class LocationInputField(
    override val name: String,
    override val label: String = name,
    val hint: String = label,
    override val defaultValue: GeoLocation? = SingleValuedField.DEFAULT_VALUE,
    override val isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    override val isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    override val validator: ((GeoLocation?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) : AbstractSingleValuedField<GeoLocation>(name, label, defaultValue, isReadonly, isRequired, validator) {
    @JsName("_ignore_fromPropery")
    constructor(
        name: KProperty<*>,
        label: String = name.name,
        hint: String = label,
        defaultValue: GeoLocation? = SingleValuedField.DEFAULT_VALUE,
        isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
        isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
        validator: ((GeoLocation?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
    ) : this(name.name, label, hint, defaultValue, isReadonly, isRequired, validator)

    override val serializer: SerializationStrategy<GeoLocation> by lazy { GeoLocation.serializer() }

    var googleApiString: String? = null

    override fun validate(value: GeoLocation?) {
        val tag = label.replaceFirstChar { it.uppercase() }
        if (isRequired && value == null) {
            throw IllegalArgumentException("$tag is required")
        }
        validator?.invoke(value)
    }
}