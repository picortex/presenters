package presenters.fields

import kollections.toIList
import kotlinx.serialization.KSerializer
import presenters.Label
import presenters.fields.internal.SingleChoiceValuedFieldImpl

inline fun <T : Any> SingleChoiceValuedField(
    name: String,
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    serializer: KSerializer<T>,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    label: Label = Label(name, isRequired),
    defaultValue: T? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY
): SingleChoiceValuedField<T> = SingleChoiceValuedFieldImpl(name, items.toIList(), mapper, serializer, isRequired, label, defaultValue, isReadonly)