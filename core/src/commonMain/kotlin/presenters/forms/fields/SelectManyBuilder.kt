package presenters.forms.fields

import kotlinx.collections.interoperable.toInteroperableList
import presenters.fields.*
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun <T : Any> Fields.selectMany(
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    name: String? = null,
    label: String? = name?.replaceFirstChar { it.uppercase() },
    value: Collection<T>? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED
) = getOrCreate { property ->
    SelectManyInputField(
        name = name ?: property.name,
        items = items,
        mapper = mapper,
        label = label ?: property.name,
        defaultValue = value?.toInteroperableList(),
        isReadonly = isReadonly,
        isRequired = isRequired,
    )
}

inline fun <T : Any> Fields.selectMany(
    name: KProperty<*>,
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    label: String? = name.name.replaceFirstChar { it.uppercase() },
    value: Collection<T>? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED
) = selectMany(items, mapper, name.name, label, value, isReadonly, isRequired)