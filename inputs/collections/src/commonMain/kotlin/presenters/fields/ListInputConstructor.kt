package presenters.fields

import kollections.serializers.ListSerializer
import kotlinx.serialization.serializer
import presenters.fields.internal.ListInputFieldImpl

inline fun <reified E> ListInput(
    name: String,
    label: String = name,
    isRequired: Boolean = true,
    isReadonly: Boolean = false,
    maxItems: Int? = null,
    minItems: Int? = null
): ListInputField<E> = ListInputFieldImpl(
    name = name,
    label = InputLabel(label, isRequired),
    isReadonly = isReadonly,
    isRequired = isRequired,
    maxItems = maxItems,
    minItems = minItems,
    serializer = ListSerializer(serializer())
)