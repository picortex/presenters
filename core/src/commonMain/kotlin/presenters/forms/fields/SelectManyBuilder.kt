package presenters.forms.fields

import kotlinx.collections.interoperable.List
import kotlinx.collections.interoperable.toInteroperableList
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.serializer
import presenters.fields.*
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun <reified T : Any> Fields.selectMany(
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    name: String? = null,
    serializer: SerializationStrategy<List<T>> = ListSerializer(serializer()),
    label: String? = name?.replaceFirstChar { it.uppercase() },
    value: Collection<T>? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED
) = getOrCreate { property ->
    SelectManyInputField(
        name = name ?: property.name,
        items = items,
        mapper = mapper,
        serializer = serializer as SerializationStrategy<Collection<T>>,
        label = label ?: property.name,
        defaultValue = value?.toInteroperableList(),
        isReadonly = isReadonly,
        isRequired = isRequired,
    )
}

inline fun <reified T : Any> Fields.selectMany(
    name: KProperty<*>,
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    serializer: SerializationStrategy<List<T>> = ListSerializer(serializer()),
    label: String? = name.name.replaceFirstChar { it.uppercase() },
    value: Collection<T>? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED
) = selectMany(items, mapper, name.name, serializer, label, value, isReadonly, isRequired)