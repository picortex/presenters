@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import kotlinx.serialization.KSerializer
import krono.LocalDate
import krono.serializers.LocalDateIsoSerializer
import presenters.fields.internal.AbstractValuedField
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.reflect.KProperty

@JsExport
class DateInputField(
    override val name: String,
    override val label: String = name,
    val hint: String = label,
    override val defaultValue: LocalDate? = ValuedField.DEFAULT_VALUE,
    override val isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
    override val isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
    val pattern: String = DEFAULT_PATTERN,
    val maxDate: LocalDate? = DEFAULT_MAX_DATE,
    val minDate: LocalDate? = DEFAULT_MIN_DATE,
    validator: ((LocalDate?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) : AbstractValuedField<LocalDate>(name, label, defaultValue, isReadonly, isRequired, validator) {
    @JsName("_ignore_fromPropery")
    constructor(
        name: KProperty<*>,
        label: String = name.name,
        hint: String = label,
        defaultValue: LocalDate? = ValuedField.DEFAULT_VALUE,
        isReadonly: Boolean = ValuedField.DEFAULT_IS_READONLY,
        isRequired: Boolean = ValuedField.DEFAULT_IS_REQUIRED,
        pattern: String = DEFAULT_PATTERN,
        maxDate: LocalDate? = DEFAULT_MAX_DATE,
        minDate: LocalDate? = DEFAULT_MIN_DATE,
        validator: ((LocalDate?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
    ) : this(name.name, label, hint, defaultValue, isReadonly, isRequired, pattern, maxDate, minDate, validator)

    override val serializer: KSerializer<LocalDate> by lazy { LocalDateIsoSerializer }
    var isoString: String?
        get() = value?.toIsoString()
        set(iso) = if (iso == null) {
            value = null
        } else try {
            value = LocalDate(iso)
        } catch (err: Throwable) {
            value = null
            feedback.value = InputFieldState.Warning("Invalid date $iso", err)
        }

    override fun validate(value: LocalDate?) {
        val tag = label.replaceFirstChar { it.uppercase() }
        if (isRequired && value == null) {
            throw IllegalArgumentException("$tag is required")
        }

        val max = maxDate
        if (max != null && value != null && value.isAfter(max)) {
            throw IllegalArgumentException("$tag must be before ${max.format(pattern)}")
        }
        val min = minDate
        if (min != null && value != null && value.isBefore(min)) {
            throw IllegalArgumentException("$tag must be after ${min.format(pattern)}")
        }
        validator?.invoke(value)
    }

    companion object {
        val DEFAULT_MAX_DATE: Nothing? = null
        val DEFAULT_MIN_DATE: Nothing? = null
        val DEFAULT_PATTERN = "{MMM} {D}, {YYYY}"
    }
}