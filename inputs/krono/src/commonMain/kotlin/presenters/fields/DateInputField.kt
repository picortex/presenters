@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.nullable
import krono.LocalDate
import krono.LocalDateOrNull
import krono.internal.InstantImpl
import krono.serializers.LocalDateIsoSerializer
import presenters.fields.internal.AbstractValuedField
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.reflect.KProperty

@JsExport
class DateInputField(
    override val name: String,
    override val isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    val hint: String = label.text,
    override val defaultValue: String? = SingleValuedField.DEFAULT_VALUE,
    override val isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    val pattern: String = DEFAULT_PATTERN,
    val maxDate: LocalDate? = DEFAULT_MAX_DATE,
    val minDate: LocalDate? = DEFAULT_MIN_DATE,
    validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) : AbstractValuedField<String, LocalDate>(name, isRequired, label, defaultValue, DEFAULT_DATE_TRANSFORMER, isReadonly, validator) {
    override val serializer: KSerializer<LocalDate?> by lazy { LocalDateIsoSerializer.nullable }

    override fun set(value: String?) {
        val res = validate(value)
        val date = LocalDateOrNull(value)
        feedback.value = when {
            res is Invalid -> InputFieldState.Warning(res.cause.message ?: "Unknown", res.cause)
            date == null -> InputFieldState.Warning("Invalid date $value", IllegalArgumentException("Invalid date $value"))
            else -> InputFieldState.Empty
        }
        input.value = value
    }

    override fun validate(value: String?): ValidationResult {
        val date = LocalDateOrNull(value)
        val tag = label.capitalizedWithoutAstrix()
        if (isRequired && date == null) {
            return Invalid(IllegalArgumentException("$tag is required"))
        }

        val max = maxDate
        if (max != null && date != null && date > max) {
            return Invalid(IllegalArgumentException("$tag must be before ${max.format(pattern)}"))
        }

        val min = minDate
        if (min != null && date != null && date < min) {
            return Invalid(IllegalArgumentException("$tag must be after ${min.format(pattern)}"))
        }

        return try {
            validator?.invoke(value)
            Valid
        } catch (err: Throwable) {
            Invalid(err)
        }
    }

    companion object {
        val DEFAULT_MAX_DATE: Nothing? = null
        val DEFAULT_MIN_DATE: Nothing? = null
        val DEFAULT_PATTERN = "{MMM} {D}, {YYYY}"
        val DEFAULT_DATE_TRANSFORMER = { iso: String? -> LocalDateOrNull(iso) }
    }
}