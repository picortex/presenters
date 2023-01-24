@file:Suppress("NON_EXPORTABLE_TYPE", "NOTHING_TO_INLINE")

package presenters

import epsilon.FileBlob
import kollections.List
import presenters.internal.MultiFileInputFieldImpl
import kotlin.reflect.KProperty

inline fun MultiFileInputField(
    name: String,
    label: String = name,
    hint: String = label,
    value: List<FileBlob>? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((List<FileBlob>?) -> Unit)? = null
): MultiFileInputField = MultiFileInputFieldImpl(
    name = name,
    label = Label(label, isRequired),
    hint = hint,
    value = value,
    isRequired = isRequired,
    isReadonly = isReadonly,
    validator = validator
)

inline fun Fields.files(
    name: String,
    label: String = name,
    hint: String = label,
    value: List<FileBlob>? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((List<FileBlob>?) -> Unit)? = null
): MultiFileInputField = getOrCreate(name) {
    MultiFileInputField(name, label, hint, value, isReadonly, isRequired, validator)
}

inline fun Fields.files(
    name: KProperty<Any?>,
    label: String = name.name,
    hint: String = label,
    value: List<FileBlob>? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((List<FileBlob>?) -> Unit)? = null
) = files(name.name, label, hint, value, isReadonly, isRequired, validator)
