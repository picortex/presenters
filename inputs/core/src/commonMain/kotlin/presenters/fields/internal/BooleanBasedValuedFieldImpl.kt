package presenters.fields.internal

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import presenters.fields.BooleanInputField
import presenters.fields.InputLabel
import presenters.fields.SingleValuedField
import presenters.fields.SingleValuedField.Companion.DEFAULT_IS_READONLY
import presenters.fields.SingleValuedField.Companion.DEFAULT_IS_REQUIRED
import presenters.validation.Invalid
import presenters.validation.Valid
import presenters.validation.ValidationResult

@PublishedApi
internal class BooleanBasedValuedFieldImpl(
    name: String,
    isRequired: Boolean = DEFAULT_IS_REQUIRED,
    label: InputLabel = InputLabel(name, isRequired),
    defaultValue: Boolean? = SingleValuedField.DEFAULT_VALUE,
    isReadonly: Boolean = DEFAULT_IS_READONLY,
    validator: ((Boolean?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) : DirectInputValuedField<Boolean>(name, isRequired, label, defaultValue, isReadonly, validator), BooleanInputField {

    override val serializer: KSerializer<Boolean> by lazy { Boolean.serializer() }

    override fun validate(value: Boolean?): ValidationResult {
        if (isRequired && value == null) {
            return Invalid(IllegalArgumentException("${label.capitalizedWithoutAstrix()} is required"))
        }

        return try {
            validator?.invoke(value)
            Valid
        } catch (err: Throwable) {
            Invalid(err)
        }
    }

    override fun toggle() = set(
        when (val value = data.value.output) {
            null -> true
            else -> !value
        }
    )

    companion object {
        val DEFAULT_MAX_LENGTH: Int? = null
        val DEFAULT_MIN_LENGTH: Int? = null
    }
}