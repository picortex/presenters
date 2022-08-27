@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields.internal

import presenters.fields.InputFieldWithValue.Companion.DEFAULT_IS_READONLY
import presenters.fields.InputFieldWithValue.Companion.DEFAULT_IS_REQUIRED
import presenters.fields.SingleValuedField
import presenters.fields.ValuedField
import kotlin.js.JsExport

abstract class BooleanBasedInputField(
    override val name: String,
    override val label: String = name,
    override val defaultValue: Boolean? = ValuedField.DEFAULT_VALUE,
    override val isReadonly: Boolean = DEFAULT_IS_READONLY,
    override val isRequired: Boolean = DEFAULT_IS_REQUIRED,
    override val validator: ((Boolean?) -> Unit)? = ValuedField.DEFAULT_VALIDATOR
) : AbstractSingleValuedField<Boolean>(name, label, defaultValue, isReadonly, isRequired, validator) {
    companion object {
        val DEFAULT_MAX_LENGTH: Int? = null
        val DEFAULT_MIN_LENGTH: Int? = null
    }

    override fun validate(value: Boolean?) {
        if (isRequired && value == null) {
            throw IllegalArgumentException("${label.replaceFirstChar { it.uppercase() }} is required")
        }
        validator?.invoke(value)
    }
}