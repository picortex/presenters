package presenters.forms.fields

import kollections.toIList
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import presenters.fields.InputLabel
import presenters.fields.Option
import presenters.fields.SingleChoiceValuedField
import presenters.fields.internal.SingleChoiceValuedFieldImpl
import presenters.fields.SingleValuedField
import presenters.forms.Fields
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : Any> Fields.selectSingle(
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    name: String,
    serializer: KSerializer<T> = serializer(),
    label: String = name.replaceFirstChar { it.uppercase() },
    value: T? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED
): SingleChoiceValuedField<T> = getOrCreate(name) {
    SingleChoiceValuedFieldImpl(
        name = name,
        items = items.toIList(),
        mapper = mapper,
        label = InputLabel(label, isReadonly),
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
    label: String = name.name.replaceFirstChar { it.uppercase() },
    value: T? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED
) = selectSingle(items, mapper, name.name, serializer, label, value, isReadonly, isRequired)