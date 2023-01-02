package presenters.fields.internal

import kotlinx.serialization.KSerializer
import live.Live
import presenters.fields.InputData
import presenters.fields.SingleValuedField.Companion.DEFAULT_IS_READONLY
import presenters.fields.SingleValuedField.Companion.DEFAULT_IS_REQUIRED
import presenters.fields.InputLabel
import presenters.validation.Invalid
import presenters.fields.TextBasedValuedField
import presenters.validation.Valid
import presenters.validation.ValidationResult
import presenters.fields.SingleValuedField.Companion.DEFAULT_VALIDATOR
import presenters.fields.TransformedInput

class TextBasedValuedFieldImpl<O : Any>(
    name: String,
    isRequired: Boolean = DEFAULT_IS_REQUIRED,
    label: InputLabel = InputLabel(name, isRequired),
    override val hint: String = label.capitalizedWithAstrix(),
    formatter: ((O?) -> String?)? = null,
    transformer: (String?) -> O?,
    defaultValue: String? = null,
    isReadonly: Boolean = DEFAULT_IS_READONLY,
    val maxLength: Int? = DEFAULT_MAX_LENGTH,
    val minLength: Int? = DEFAULT_MIN_LENGTH,
    validator: ((String?) -> Unit)? = DEFAULT_VALIDATOR,
    override val serializer: KSerializer<O>,
) : FormattedInputValuedField<String, O>(name, isRequired, label, defaultValue, formatter, transformer, isReadonly, validator), TextBasedValuedField<O>, TransformedInput<String, O> {

    companion object {
        val DEFAULT_MAX_LENGTH: Int? = null
        val DEFAULT_MIN_LENGTH: Int? = null
    }

    override fun set(value: String?) {
        setRaw(value)
        data.value = toInputData(value)
    }

    override fun validate(value: String?): ValidationResult {
        if (isRequired && value.isNullOrBlank()) {
            return Invalid(IllegalArgumentException("${label.capitalizedWithoutAstrix()} is required"))
        }
        val max = maxLength
        if (max != null && value != null && value.length > max) {
            return Invalid(IllegalArgumentException("${label.capitalizedWithoutAstrix()} must have less than $max characters"))
        }
        val min = minLength
        if (min != null && value != null && value.length < min) {
            return Invalid(IllegalArgumentException("${label.capitalizedWithoutAstrix()} must have more than $min characters"))
        }

        return try {
            validator?.invoke(value)
            Valid
        } catch (err: Throwable) {
            Invalid(err)
        }
    }

    override fun type(text: String) {
        val old = data.value.raw ?: ""
        for (i in 0..text.lastIndex) set(old + text.substring(0..i))
    }
}