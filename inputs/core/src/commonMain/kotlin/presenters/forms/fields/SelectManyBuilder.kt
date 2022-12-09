package presenters.forms.fields

import kollections.Collection
import kollections.List
import kollections.serializers.ListSerializer
import kollections.toIList
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import presenters.fields.InputLabel
import presenters.fields.Option
import presenters.fields.SelectManyInputField
import presenters.fields.ValuedField
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun <reified T : Any> Fields.selectMany(
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    name: String? = null,
    serializer: KSerializer<List<T>> = ListSerializer(serializer()),
    label: String? = name?.replaceFirstChar { it.uppercase() },
    value: Collection<T>? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED
) = getOrCreate { property ->
    SelectManyInputField(
        name = name ?: property.name,
        items = items,
        mapper = mapper,
        serializer = serializer,
        label = InputLabel(label ?: property.name,isReadonly),
        defaultValue = value?.toIList(),
        isReadonly = isReadonly,
        isRequired = isRequired,
    )
}

inline fun <reified T : Any> Fields.selectMany(
    name: KProperty<*>,
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    serializer: KSerializer<List<T>> = ListSerializer(serializer()),
    label: String? = name.name.replaceFirstChar { it.uppercase() },
    value: Collection<T>? = ValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED
) = selectMany(items, mapper, name.name, serializer, label, value, isReadonly, isRequired)