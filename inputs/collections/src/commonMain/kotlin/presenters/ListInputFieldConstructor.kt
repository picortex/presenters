package presenters

import kollections.iEmptyList
import kollections.serializers.ListSerializer
import kotlinx.serialization.serializer
import presenters.internal.ListInputFieldImpl
import kotlin.reflect.KProperty

inline fun <reified E> ListInputField(
    name: String,
    label: String = name,
    hint: String = label,
    value: Collection<E>? = null,
    isRequired: Boolean = true,
    isReadonly: Boolean = false,
    maxItems: Int? = null,
    minItems: Int? = null
): ListInputField<E> = ListInputFieldImpl(
    name = name,
    label = Label(label, isRequired),
    hint = hint,
    value = value ?: iEmptyList(),
    isReadonly = isReadonly,
    isRequired = isRequired,
    maxItems = maxItems,
    minItems = minItems,
    serializer = ListSerializer(serializer())
)

inline fun <reified E> Fields.list(
    name: String,
    label: String = name,
    hint: String = label,
    value: Collection<E>? = null,
    isRequired: Boolean = true,
    isReadonly: Boolean = false,
    maxItems: Int? = null,
    minItems: Int? = null
): ListInputField<E> = getOrCreate(name) {
    ListInputField(name, label, hint, value, isRequired, isReadonly, maxItems, minItems)
}

inline fun <reified E> Fields.list(
    name: KProperty<Any?>,
    label: String = name.name,
    hint: String = label,
    value: Collection<E>? = null,
    isRequired: Boolean = true,
    isReadonly: Boolean = false,
    maxItems: Int? = null,
    minItems: Int? = null
) = list(name.name, label, hint, value, isRequired, isReadonly, maxItems, minItems)
