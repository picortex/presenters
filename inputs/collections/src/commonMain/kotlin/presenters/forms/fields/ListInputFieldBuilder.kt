package presenters.forms.fields

import presenters.fields.ListInputField
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun <reified E> Fields.list(
    name: String,
    label: String = name,
    value: Collection<E>? = null,
    isRequired: Boolean = true,
    isReadonly: Boolean = false,
    maxItems: Int? = null,
    minItems: Int? = null
): ListInputField<E> = getOrCreate(name) {
    ListInputField(
        name = name,
        label = label,
        value = value,
        isReadonly = isReadonly,
        isRequired = isRequired,
        maxItems = maxItems,
        minItems = minItems,
    )
}

inline fun <reified E> Fields.list(
    name: KProperty<*>,
    label: String = name.name,
    value: Collection<E>? = null,
    isRequired: Boolean = true,
    isReadonly: Boolean = false,
    maxItems: Int? = null,
    minItems: Int? = null
) = list(name.name, label, value, isRequired, isReadonly, maxItems, minItems)