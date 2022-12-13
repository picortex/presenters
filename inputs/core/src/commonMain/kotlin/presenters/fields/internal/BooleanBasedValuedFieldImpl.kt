package presenters.fields.internal

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import presenters.fields.BooleanBasedValuedField
import presenters.fields.InputFieldWithValue.Companion.DEFAULT_IS_READONLY
import presenters.fields.InputFieldWithValue.Companion.DEFAULT_IS_REQUIRED
import presenters.fields.InputLabel
import presenters.fields.Invalid
import presenters.fields.SingleValuedField
import presenters.fields.Valid
import presenters.fields.ValidationResult

class BooleanBasedValuedFieldImpl(
    override val name: String,
    override val isRequired: Boolean = DEFAULT_IS_REQUIRED,
    override val label: InputLabel = InputLabel(name, isRequired),
    override val defaultValue: Boolean? = SingleValuedField.DEFAULT_VALUE,
    override val isReadonly: Boolean = DEFAULT_IS_READONLY,
    validator: ((Boolean?) -> Unit)? = SingleValuedField.DEFAULT_VALIDATOR
) : AbstractValuedField<Boolean, Boolean>(name, isRequired, label, defaultValue, { it }, isReadonly, validator), BooleanBasedValuedField {
    companion object {
        val DEFAULT_MAX_LENGTH: Int? = null
        val DEFAULT_MIN_LENGTH: Int? = null
    }

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
}