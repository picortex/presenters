@file:JsExport
@file:Suppress("WRONG_EXPORTED_DECLARATION", "NON_EXPORTABLE_TYPE")

package presenters.forms

import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import kotlinx.serialization.builtins.nullable
import presenters.fields.InputField
import presenters.fields.InputFieldState
import presenters.fields.ValuedField
import presenters.fields.internal.AbstractValuedField
import kotlin.js.JsExport

open class Fields(internal val cache: MutableMap<String, InputField> = mutableMapOf()) {

    internal val all get() = cache.values

    fun encodedValuesToJson(codec: StringFormat) = valuesToBeSubmitted.associate {
        it.name to it
    }.toList().joinToString(prefix = "{", postfix = "\n}") { (key, input) ->
        val serializer = input.serializer as KSerializer<Any>
        """${"\n"}    "$key": ${codec.encodeToString(serializer.nullable, input.field.value)}"""
    }

    internal val allInvalid get() = valuesToBeSubmitted.filter { it.feedback.value is InputFieldState.Error }

    private val valueFields get() = cache.values.filterIsInstance<AbstractValuedField<*>>()

    internal val valuesToBeSubmitted
        get() = valueFields.filterNot {
            !it.isRequired && (it.field.value == null || it.field.value.toString().isBlank())
        }

    fun validate() {
        valuesToBeSubmitted.forEach { it.validateWithFeedback() }
    }

    fun clearAll() {
        valueFields.forEach { it.clear() }
    }
}