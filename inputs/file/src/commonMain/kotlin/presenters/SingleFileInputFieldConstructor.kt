@file:Suppress("NOTHING_TO_INLINE")

package presenters

import epsilon.FileBlob
import presenters.internal.SingleFileInputFieldImpl
import kotlin.reflect.KProperty


inline fun SingleFileInputField(
    name: String,
    label: String = name,
    hint: String = label,
    value: FileBlob? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((FileBlob?) -> Unit)? = null
): SingleFileInputField = SingleFileInputFieldImpl(
    name = name,
    label = Label(label, isRequired),
    hint = hint,
    value = value,
    isRequired = isRequired,
    isReadonly = isReadonly,
    validator = validator
)

inline fun Fields.file(
    name: String,
    label: String = name,
    hint: String = label,
    value: FileBlob? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((FileBlob?) -> Unit)? = null
): SingleFileInputField = getOrCreate(name) {
    SingleFileInputField(name, label, hint, value, isReadonly, isRequired, validator)
}

inline fun Fields.file(
    name: KProperty<Any?>,
    label: String = name.name,
    hint: String = label,
    value: FileBlob? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((FileBlob?) -> Unit)? = null
) = file(name.name, label, hint, value, isReadonly, isRequired, validator)