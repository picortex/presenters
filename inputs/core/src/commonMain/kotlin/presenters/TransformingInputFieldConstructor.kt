package presenters

import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import presenters.internal.TransformingInputFieldImpl
import kotlin.reflect.KProperty

inline fun <I : Any, reified O : Any> TransformingInputField(
    name: String,
    label: String = name,
    hint: String = label,
    value: O? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    serializer: KSerializer<O> = serializer(),
    formatter: Formatter<O>? = null,
    noinline validator: ((O?) -> Unit)? = null,
    noinline transformer: (I?) -> O?
): TransformingInputField<I, O> = TransformingInputFieldImpl(
    name = name,
    label = Label(label, isRequired),
    hint = hint,
    value = value,
    trnsfrmr = transformer,
    serializer = serializer,
    isReadonly = isReadonly,
    isRequired = isRequired,
    formatter = formatter,
    validator = validator,
)

inline fun <I : Any, reified O : Any> Fields.transform(
    name: String,
    label: String = name,
    hint: String = label,
    value: O? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    serializer: KSerializer<O> = serializer(),
    formatter: Formatter<O>? = null,
    noinline validator: ((O?) -> Unit)? = null,
    noinline transformer: (I?) -> O?
): TransformingInputField<I, O> = getOrCreate(name) {
    TransformingInputField(name, label, hint, value, isReadonly, isRequired, serializer, formatter, validator, transformer)
}

inline fun <I : Any, reified O : Any> Fields.transform(
    name: KProperty<O?>,
    label: String = name.name,
    hint: String = label,
    value: O? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    serializer: KSerializer<O> = serializer(),
    formatter: Formatter<O>? = null,
    noinline validator: ((O?) -> Unit)? = null,
    noinline transformer: (I?) -> O?
) = transform(name.name, label, hint, value, isReadonly, isRequired, serializer, formatter, validator, transformer)