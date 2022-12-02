@file:JsExport @file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.fields.internal

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.builtins.serializer
import presenters.fields.InputFieldWithValue.Companion.DEFAULT_IS_READONLY
import presenters.fields.InputFieldWithValue.Companion.DEFAULT_IS_REQUIRED
import presenters.fields.ValuedField
import presenters.fields.ValuedField.Companion.DEFAULT_VALIDATOR
import presenters.fields.ValuedField.Companion.DEFAULT_VALUE
import kotlin.js.JsExport

abstract class TextBasedValueField(
    override val name: String,
    override val label: String = name,
    open val hint: String = label,
    defaultValue: String? = DEFAULT_VALUE,
    override val isReadonly: Boolean = DEFAULT_IS_READONLY,
    override val isRequired: Boolean = DEFAULT_IS_REQUIRED,
    open val maxLength: Int? = DEFAULT_MAX_LENGTH,
    open val minLength: Int? = DEFAULT_MIN_LENGTH,
    validator: ((String?) -> Unit)? = DEFAULT_VALIDATOR
) : AbstractValuedField<String>(name, label, defaultValue, isReadonly, isRequired, validator) {
    companion object {
        val DEFAULT_MAX_LENGTH: Int? = null
        val DEFAULT_MIN_LENGTH: Int? = null
    }

    override val serializer: KSerializer<String> by lazy { String.serializer() }

    val valueOrNullIfEmpty get() = if (value.isNullOrEmpty()) null else value

    override fun validate(value: String?) {
        if (isRequired && value.isNullOrBlank()) {
            throw IllegalArgumentException("${label.replaceFirstChar { it.uppercase() }} is required")
        }
        val max = maxLength
        if (max != null && value != null && value.length > max) {
            throw IllegalArgumentException("${label.replaceFirstChar { it.uppercase() }} must have less than $max characters")
        }
        val min = minLength
        if (min != null && value != null && value.length < min) {
            throw IllegalArgumentException("${label.replaceFirstChar { it.uppercase() }} must have more than $min characters")
        }
        validator?.invoke(value)
    }
}