package presenters

import kollections.toIList
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import presenters.forms.Fields
import presenters.forms.fields.getOrCreate
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
    isRequired: Boolean = false
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
    isRequired: Boolean = false
): SingleChoiceInputField<T> = getOrCreate(name) {
    SingleChoiceInputField(name, items, mapper, serializer, label, hint, value, isReadonly, isRequired)
}

inline fun <reified T : Any> Fields.selectSingle(
    name: KProperty<*>,
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    serializer: KSerializer<T> = serializer(),
    label: String = name.name.replaceFirstChar { it.uppercase() },
    hint: String = label,
    value: T? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false
) = selectSingle(name.name, items, mapper, serializer, label, hint, value, isReadonly, isRequired)