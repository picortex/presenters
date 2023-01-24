package presenters

import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import kotlin.reflect.KProperty

inline fun <reified O : Any> Fields.textTo(
    name: String,
    label: String = name,
    hint: String = label,
    value: O? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    serializer: KSerializer<O> = serializer(),
    formatter: Formatter<O>? = null,
    noinline validator: ((O?) -> Unit)? = null,
    noinline transformer: (String?) -> O?
): TransformingInputField<String, O> = getOrCreate(name) {
    TransformingInputField(name, label, hint, value, isReadonly, isRequired, serializer, formatter, validator, transformer)
}

inline fun <reified O : Any> Fields.textTo(
    name: KProperty<O?>,
    label: String = name.name,
    hint: String = label,
    value: O? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    serializer: KSerializer<O> = serializer(),
    formatter: Formatter<O>? = null,
    noinline validator: ((O?) -> Unit)? = null,
    noinline transformer: (String?) -> O?
) = textTo(name.name, label, hint, value, isReadonly, isRequired, serializer, formatter, validator, transformer)