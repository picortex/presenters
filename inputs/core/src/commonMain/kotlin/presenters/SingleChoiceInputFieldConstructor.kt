package presenters

import kollections.toIList
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import presenters.internal.choices.SingleChoiceInputFieldImpl
import kotlin.reflect.KProperty

inline fun <reified T : Any> SingleChoiceInputField(
    name: String,
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    serializer: KSerializer<T> = serializer(),
    label: String = name.replaceFirstChar { it.uppercase() },
    hint: String = label,
    value: T? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((T?) -> Unit)? = null
): SingleChoiceInputField<T> = SingleChoiceInputFieldImpl(
    name = name,
    items = items.toIList(),
    mapper = mapper,
    label = Label(label, isReadonly),
    hint = hint,
    value = value,
    serializer = serializer,
    isReadonly = isReadonly,
    isRequired = isRequired,
    validator = validator
)

inline fun <reified T : Any> Fields.selectSingle(
    name: String,
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    serializer: KSerializer<T> = serializer(),
    label: String = name.replaceFirstChar { it.uppercase() },
    hint: String = label,
    value: T? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((T?) -> Unit)? = null
): SingleChoiceInputField<T> = getOrCreate(name) {
    SingleChoiceInputField(name, items, mapper, serializer, label, hint, value, isReadonly, isRequired, validator)
}

inline fun <reified T : Any> Fields.selectSingle(
    name: KProperty<T?>,
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    serializer: KSerializer<T> = serializer(),
    label: String = name.name.replaceFirstChar { it.uppercase() },
    hint: String = label,
    value: T? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((T?) -> Unit)? = null
) = selectSingle(name.name, items, mapper, serializer, label, hint, value, isReadonly, isRequired, validator)