package presenters.fields

import kollections.iEmptyList
import kollections.serializers.ListSerializer
import kotlinx.serialization.serializer
import presenters.fields.internal.ListInputFieldImpl

inline fun <reified E> ListInputField(
    name: String,
    label: String = name,
    value: Collection<E>? = null,
    isRequired: Boolean = true,
    isReadonly: Boolean = false,
    maxItems: Int? = null,
    minItems: Int? = null
): ListInputField<E> = ListInputFieldImpl(
    name = name,
    label = InputLabel(label, isRequired),
    defaultValue = value ?: iEmptyList(),
    isReadonly = isReadonly,
    isRequired = isRequired,
    maxItems = maxItems,
    minItems = minItems,
    serializer = ListSerializer(serializer())
)