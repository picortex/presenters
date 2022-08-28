package presenters.forms.fields

import presenters.fields.*
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun <T : Any> Fields.selectSingle(
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    name: String? = null,
    label: String? = name?.replaceFirstChar { it.uppercase() },
    value: T? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED
) = getOrCreate { property ->
    SelectSingleInputField(
        name = name ?: property.name,
        items = items,
        mapper = mapper,
        label = label ?: property.name,
        defaultValue = value,
        isReadonly = isReadonly,
        isRequired = isRequired,
    )
}

inline fun <T : Any> Fields.selectSingle(
    name: KProperty<*>,
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    label: String? = name.name.replaceFirstChar { it.uppercase() },
    value: T? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED
) = selectSingle(items, mapper, name.name, label, value, isReadonly, isRequired)