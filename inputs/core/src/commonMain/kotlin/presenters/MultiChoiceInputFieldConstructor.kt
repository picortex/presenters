package presenters

import kollections.List
import kollections.serializers.ListSerializer
import kollections.toIList
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import presenters.internal.choices.MultiChoiceInputFieldImpl
import kotlin.reflect.KProperty

inline fun <reified T : Any> MultiChoiceInputField(
    name: String,
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    isRequired: Boolean = false,
    label: String = name.replaceFirstChar { it.uppercase() },
    hint: String = label,
    serializer: KSerializer<List<T>> = ListSerializer(serializer()),
    value: Collection<T>? = null,
    isReadonly: Boolean = false,
    noinline validator: ((List<T>?) -> Unit)? = null
): MultiChoiceInputField<T> = MultiChoiceInputFieldImpl(
    name = name,
    items = items.toIList(),
    mapper = mapper,
    hint = hint,
    value = value?.toIList(),
    serializer = serializer,
    label = Label(label, isReadonly),
    isReadonly = isReadonly,
    isRequired = isRequired,
    validator = validator
)

inline fun <reified T : Any> Fields.selectMany(
    name: String,
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    isRequired: Boolean = false,
    label: String = name.replaceFirstChar { it.uppercase() },
    hint: String = label,
    serializer: KSerializer<List<T>> = ListSerializer(serializer()),
    value: Collection<T>? = null,
    isReadonly: Boolean = false,
    noinline validator: ((List<T>?) -> Unit)? = null
): MultiChoiceInputField<T> = getOrCreate(name) {
    MultiChoiceInputField(name, items, mapper, isRequired, label, hint, serializer, value, isReadonly, validator)
}

inline fun <reified T : Any> Fields.selectMany(
    name: KProperty<Collection<T>?>,
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    isRequired: Boolean = false,
    label: String = name.name.replaceFirstChar { it.uppercase() },
    hint: String = label,
    serializer: KSerializer<List<T>> = ListSerializer(serializer()),
    value: Collection<T>? = null,
    isReadonly: Boolean = false,
    noinline validator: ((List<T>?) -> Unit)? = null
) = selectMany(name.name, items, mapper, isRequired, label, hint, serializer, value, isReadonly, validator)