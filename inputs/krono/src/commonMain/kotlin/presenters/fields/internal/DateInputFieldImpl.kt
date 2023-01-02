package presenters.fields.internal

import krono.LocalDate
import krono.LocalDateOrNull
import krono.serializers.LocalDateIsoSerializer
import presenters.fields.InputFieldState
import presenters.fields.InputLabel
import presenters.fields.SingleValuedField
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

@PublishedApi
internal class DateInputFieldImpl(
    name: String,
    isRequired: Boolean = SingleValuedField.DEFAULT_IS_REQUIRED,
    label: InputLabel = InputLabel(name, isRequired),
    hint: String = label.text,
    defaultValue: String? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = SingleValuedField.DEFAULT_IS_READONLY,
    val pattern: String = DEFAULT_PATTERN,
    val maxDate: LocalDate? = DEFAULT_MAX_DATE,
    val minDate: LocalDate? = DEFAULT_MIN_DATE,
    validator: ((String?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) : TextBasedValuedFieldImpl<LocalDate>(
    name = name, isRequired = isRequired, label = label,
    hint = hint, formatter = null, transformer = DEFAULT_DATE_TRANSFORMER,
    isReadonly = isReadonly, validator = validator, serializer = LocalDateIsoSerializer
) {
    override fun set(value: String?) {
        val res = validate(value)
        val date = LocalDateOrNull(value)
        feedback.value = when {
            res is Invalid -> InputFieldState.Warning(res.cause.message ?: "Unknown", res.cause)
            date == null -> InputFieldState.Warning("Invalid date $value", IllegalArgumentException("Invalid date $value"))
            else -> InputFieldState.Empty
        }
        data.value = toFormattedData(value)
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