@file:Suppress("NOTHING_TO_INLINE")

package presenters

import krono.LocalDate
import presenters.internal.DateInputFieldImpl
import presenters.forms.Fields
import presenters.forms.fields.getOrCreate
import kotlin.reflect.KProperty

inline fun DateInputField(
    name: String,
    isRequired: Boolean = false,
    label: String = name,
    hint: String = label,
    value: LocalDate? = null,
    isReadonly: Boolean = false,
    pattern: String = DateInputFieldImpl.DEFAULT_PATTERN,
    maxDate: LocalDate? = null,
    minDate: LocalDate? = null,
    noinline validator: ((LocalDate?) -> Unit)? = null
): DateInputField = DateInputFieldImpl(name, isRequired, Label(label, isRequired), hint, value, isReadonly, pattern, maxDate, minDate, validator)

inline fun Fields.date(
    name: String,
    isRequired: Boolean = false,
    label: String = name,
    hint: String = label,
    value: LocalDate? = null,
    isReadonly: Boolean = false,
    pattern: String = DateInputFieldImpl.DEFAULT_PATTERN,
    maxDate: LocalDate? = null,
    minDate: LocalDate? = null,
    noinline validator: ((LocalDate?) -> Unit)? = null
) = getOrCreate(name) {
    DateInputField(name, isRequired, label, hint, value, isReadonly, pattern, maxDate, minDate, validator)
}

inline fun Fields.date(
    name: KProperty<*>,
    isRequired: Boolean = false,
    label: String = name.name,
    hint: String = label,
    value: LocalDate? = null,
    isReadonly: Boolean = false,
    pattern: String = DateInputFieldImpl.DEFAULT_PATTERN,
    maxDate: LocalDate? = null,
    minDate: LocalDate? = null,
    noinline validator: ((LocalDate?) -> Unit)? = null
) = date(name.name, isRequired, label, hint, value, isReadonly, pattern, maxDate, minDate, validator)