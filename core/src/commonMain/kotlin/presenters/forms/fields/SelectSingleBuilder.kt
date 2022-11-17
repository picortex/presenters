package presenters.forms.fields

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.serializer
import presenters.fields.*
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun <reified T : Any> Fields.selectSingle(
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    name: String? = null,
    serializer: KSerializer<T> = serializer(),
    label: String? = name?.replaceFirstChar { it.uppercase() },
    value: T? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED
) = getOrCreate { property ->
    SelectSingleInputField(
        name = name ?: property.name,
        items = items,
        mapper = mapper,
        label = label ?: property.name,
        defaultValue = value,
        serializer = serializer,
        isReadonly = isReadonly,
        isRequired = isRequired,
    )
}

inline fun <reified T : Any> Fields.selectSingle(
    name: KProperty<*>,
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    serializer: KSerializer<T> = serializer(),
    label: String? = name.name.replaceFirstChar { it.uppercase() },
    value: T? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED
) = selectSingle(items, mapper, name.name, serializer, label, value, isReadonly, isRequired)