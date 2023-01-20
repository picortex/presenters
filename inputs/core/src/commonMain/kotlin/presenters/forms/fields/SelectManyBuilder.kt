package presenters.forms.fields

import kollections.Collection
import kollections.List
import kollections.serializers.ListSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import presenters.Label
import presenters.fields.MultiChoiceValuedField
import presenters.fields.Option
import presenters.fields.internal.MultiChoiceSelectValueFieldImpl
import presenters.fields.SingleValuedField
import presenters.forms.Fields
import kotlin.reflect.KProperty

inline fun <reified T : Any> Fields.selectMany(
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    name: String,
    serializer: KSerializer<List<T>> = ListSerializer(serializer()),
    label: String = name.replaceFirstChar { it.uppercase() },
    value: Collection<T>? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED
): MultiChoiceValuedField<T> = getOrCreate(name) {
    MultiChoiceSelectValueFieldImpl(
        name = name,
        items = items,
        mapper = mapper,
        serializer = serializer,
        label = Label(label, isReadonly),
        isReadonly = isReadonly,
        isRequired = isRequired,
    )
}

inline fun <reified T : Any> Fields.selectMany(
    name: KProperty<*>,
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    serializer: KSerializer<List<T>> = ListSerializer(serializer()),
    label: String = name.name.replaceFirstChar { it.uppercase() },
    value: Collection<T>? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED
) = selectMany(items, mapper, name.name, serializer, label, value, isReadonly, isRequired)